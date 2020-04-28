package com.diligrp.message.provider;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.metadata.BatchProviderMeta;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.provider.BatchDisplayTextProviderSupport;
import com.dili.uap.sdk.domain.DataDictionaryValue;
import com.dili.uap.sdk.rpc.DataDictionaryRpc;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <B>数据字典Provider</B>
 * 此方法会把原值拼接在转换后的值后边，如 XX(code)
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/8/19 10:54
 */
@Component
@Scope("prototype")
public class DataDictionaryWithOriginalProvider extends BatchDisplayTextProviderSupport {

    //前台需要传入的参数
    protected static final String DD_CODE_KEY = "dd_code";

    @Autowired
    private DataDictionaryRpc dataDictionaryRpc;

    @Override
    public List<ValuePair<?>> getLookupList(Object val, Map metaMap, FieldMeta fieldMeta) {
        Object queryParams = metaMap.get(QUERY_PARAMS_KEY);
        if(queryParams == null) {
            return Lists.newArrayList();
        }
        List<ValuePair<?>> valuePairs = Lists.newArrayList();
        BaseOutput<List<DataDictionaryValue>> output = dataDictionaryRpc.listDataDictionaryValueByDdCode(getDdCode(queryParams.toString()));
        if(output.isSuccess() && CollectionUtil.isNotEmpty(output.getData())){
            valuePairs = output.getData().stream().filter(Objects::nonNull).sorted(Comparator.comparing(DataDictionaryValue::getId)).map(t -> {
                ValuePairImpl<?> vp = new ValuePairImpl<>(t.getName(), t.getCode());
                return vp;
            }).collect(Collectors.toList());
        }
        return valuePairs;
    }

    @Override
    protected BatchProviderMeta getBatchProviderMeta(Map metaMap) {
        BatchProviderMeta batchProviderMeta = DTOUtils.newDTO(BatchProviderMeta.class);
        //设置主DTO和关联DTO需要转义的字段名
        batchProviderMeta.setEscapeFiled("name");
        //忽略大小写关联
        batchProviderMeta.setIgnoreCaseToRef(true);
        //主DTO与关联DTO的关联(java bean)属性(外键)
        //batchProviderMeta.setFkField("ddCode");
        //关联(数据库)表的主键的字段名
        batchProviderMeta.setRelationTablePkField("code");
        batchProviderMeta.setMismatchHandler((t) -> "-");
        return batchProviderMeta;
    }

    @Override
    protected List getFkList(List<String> relationIds, Map metaMap) {
        Object queryParams = metaMap.get(QUERY_PARAMS_KEY);
        if (queryParams == null) {
            return Lists.newArrayList();
        }
        BaseOutput<List<DataDictionaryValue>> output = dataDictionaryRpc.listDataDictionaryValueByDdCode(getDdCode(queryParams.toString()));
        if (null != output && output.isSuccess()) {
            List<DataDictionaryValue> data = output.getData();
            if (CollectionUtil.isNotEmpty(data)) {
                data.forEach(t -> {
                    t.setName(t.getName() + "【" + t.getCode() + "】");
                });
            }
            return data;
        }
        return null;
    }


    /**
     * 获取数据字典编码
     * @return
     */
    private String getDdCode(String queryParams){
        //清空缓存
        String ddCode = JSONObject.parseObject(queryParams).getString(DD_CODE_KEY);
        if(ddCode == null){
            throw new RuntimeException("dd_code属性为空");
        }
        return ddCode;
    }
}
