package com.diligrp.message.provider;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONPath;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.metadata.BatchProviderMeta;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.provider.BatchDisplayTextProviderSupport;
import com.dili.uap.sdk.domain.Firm;
import com.dili.uap.sdk.domain.dto.FirmDto;
import com.diligrp.message.service.remote.FirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/1 16:23
 */
@Component
public class FirmProvider extends BatchDisplayTextProviderSupport {

    @Autowired
    private FirmService firmService;

    @Override
    public List<ValuePair<?>> getLookupList(Object obj, Map metaMap, FieldMeta fieldMeta) {
        //是否要显示集团
        Object withGroupOptValue = JSONPath.read(String.valueOf(metaMap.get("queryParams")), "/withGroupOpt");
        //是否显示所有
        Object showAll = JSONPath.read(String.valueOf(metaMap.get("queryParams")), "/showAll");
        List<Firm> list = "true".equalsIgnoreCase(String.valueOf(showAll)) ? firmService.listByExample(DTOUtils.newDTO(FirmDto.class)) : firmService.getCurrentUserFirms();
        List<ValuePair<?>> resultList = list.stream().filter((f) -> {
            if (Boolean.FALSE.equals(withGroupOptValue) && f.getCode().equalsIgnoreCase("group")) {
                return false;
            }
            return true;
        }).map(f -> (ValuePair<?>) new ValuePairImpl(f.getName(), f.getCode())).collect(Collectors.toCollection(() -> new ArrayList<>()));
        return resultList;
    }

    @Override
    protected List getFkList(List<String> relationIds, Map metaMap) {
        if (relationIds != null) {
            List<String> firmCodes = relationIds.stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            if (!firmCodes.isEmpty()) {
                FirmDto firmDto = DTOUtils.newDTO(FirmDto.class);
                firmDto.setCodes(firmCodes);
                List<Firm> firms = firmService.listByExample(firmDto);
                if (CollectionUtil.isNotEmpty(firms)) {
                    //是否需要带上原始值 true 需要
                    Object withOriginal = JSONPath.read(String.valueOf(metaMap.get(QUERY_PARAMS_KEY)), "/withOriginal");
                    if (StrUtil.equalsIgnoreCase("true", String.valueOf(withOriginal))) {
                        firms.forEach(t -> {
                            t.setName(t.getName() + "【" + t.getCode() + "】");
                        });
                    }
                }
                return firms;
            }
        }
        return null;
    }

    @Override
    protected BatchProviderMeta getBatchProviderMeta(Map metaMap) {
        BatchProviderMeta batchProviderMeta = DTOUtils.newInstance(BatchProviderMeta.class);
        //设置主DTO和关联DTO需要转义的字段名
        batchProviderMeta.setEscapeFiled("name");
        //忽略大小写关联
        batchProviderMeta.setIgnoreCaseToRef(true);
        //关联(数据库)表的主键的字段名，默认取id
        batchProviderMeta.setRelationTablePkField("code");
        //当未匹配到数据时，返回的值
        batchProviderMeta.setMismatchHandler(t -> "-");
        return batchProviderMeta;
    }

    public String getDisplayText(Object obj, Map metaMap, FieldMeta fieldMeta) {
        if (Objects.nonNull(obj)) {
            Optional<Firm> optionalFirm = Optional.empty();
            if (metaMap.containsKey("by") && Objects.equals(metaMap.get("by"),"id")){
                optionalFirm = firmService.getById(Long.valueOf(obj.toString()));
            }else{
                optionalFirm = firmService.getByCode(obj.toString());
            }
            return optionalFirm.isPresent() ? optionalFirm.get().getName() : "";
        }
        return "";
    }
}
