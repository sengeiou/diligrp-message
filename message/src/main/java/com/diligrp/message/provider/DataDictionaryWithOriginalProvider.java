package com.diligrp.message.provider;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.commons.glossary.EnabledStateEnum;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.metadata.BatchProviderMeta;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.provider.BatchDisplayTextProviderSupport;
import com.dili.uap.sdk.domain.DataDictionaryValue;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.service.remote.DataDictionaryRpcService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Component
@Scope("prototype")
public class DataDictionaryWithOriginalProvider extends BatchDisplayTextProviderSupport {

    //前台需要传入的参数
    protected static final String DD_CODE_KEY = "dd_code";
    //状态标记
    private static final String DD_ENABLE_KEY = "dd_enable";
    //查询数据字典，是否需要根据当前市场过滤
    private static final String DD_WITH_MARKET = "dd_with_market";

    private final DataDictionaryRpcService dataDictionaryRpcService;

    @Override
    public List<ValuePair<?>> getLookupList(Object val, Map metaMap, FieldMeta fieldMeta) {
        Object queryParams = metaMap.get(QUERY_PARAMS_KEY);
        if(queryParams == null) {
            return Lists.newArrayList();
        }
        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(queryParams));
        List<DataDictionaryValue> dataList = produceDataList(jsonObject);
        List<ValuePair<?>> valuePairs = Lists.newArrayList();
        if(CollectionUtil.isNotEmpty(dataList)){
            valuePairs = dataList.stream().filter(Objects::nonNull).sorted(Comparator.comparing(DataDictionaryValue::getId)).map(t -> {
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
        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(queryParams));
        List<DataDictionaryValue> dataList = produceDataList(jsonObject);
        if (CollectionUtil.isNotEmpty(dataList)) {
            dataList.forEach(t -> {
                t.setName(t.getName() + "【" + t.getCode() + "】");
            });
        }
        return dataList;
    }


    /**
     * 获取数据字典编码
     * @return
     */
    private String getDdCode(JSONObject jsonObject) {
        String ddCode = jsonObject.getString(DD_CODE_KEY);
        if (ddCode == null) {
            throw new RuntimeException("dd_code属性为空");
        }
        return ddCode;
    }

    /**
     * 组装查询条件
     *
     * @param jsonObject 外部传入的条件
     */
    private List<DataDictionaryValue> produceDataList(JSONObject jsonObject) {
        String ddCode = getDdCode(jsonObject);
        Integer state = getDdState(jsonObject);
        Long marketId = null;
        //如果需要关联市场，则需要获取当前市场信息
        Boolean aBoolean = jsonObject.getBoolean(DD_WITH_MARKET);
        if (Objects.isNull(aBoolean) || Boolean.TRUE.equals(aBoolean)) {
            marketId = getUserTicket().getFirmId();
        }
        return dataDictionaryRpcService.listByDdCode(ddCode, state, marketId);
    }

    /**
     * 获取检索数据字典的状态
     * @param jsonObject
     * @return
     */
    private Integer getDdState(JSONObject jsonObject) {
        Boolean ddEnable = jsonObject.getBoolean(DD_ENABLE_KEY);
        if (Boolean.TRUE.equals(ddEnable)) {
            return EnabledStateEnum.ENABLED.getCode();
        }
        return null;
    }

    /**
     * 获取登录用户信息 如为null则new一个，以免空指针
     *
     * @return
     */
    private UserTicket getUserTicket() {
        SessionContext sessionContext = SessionContext.getSessionContext();
        UserTicket userTicket = sessionContext.getUserTicket();
        if (userTicket == null) {
            userTicket = DTOUtils.newInstance(UserTicket.class);
        }
        return userTicket;
    }
}
