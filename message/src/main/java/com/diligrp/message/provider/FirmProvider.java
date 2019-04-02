package com.diligrp.message.provider;

import com.alibaba.fastjson.JSONPath;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.provider.BatchDisplayTextProviderAdaptor;
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
public class FirmProvider extends BatchDisplayTextProviderAdaptor {

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
        }).map(f -> {
            return (ValuePair<?>) new ValuePairImpl(f.getName(), f.getCode());
        }).collect(Collectors.toCollection(() -> new ArrayList<ValuePair<?>>()));
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
                return firms;
            }
        }
        return null;
    }

    @Override
    protected Map<String, String> getEscapeFileds(Map metaMap) {
        if (metaMap.get(ESCAPE_FILEDS_KEY) instanceof Map) {
            return (Map) metaMap.get(ESCAPE_FILEDS_KEY);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put(metaMap.get(FIELD_KEY).toString(), "name");
            return map;
        }
    }

    @Override
    protected boolean ignoreCaseToRef() {
        return true;
    }

    /**
     * 关联(数据库)表的主键的字段名
     * 默认取id，子类可自行实现
     * @return
     */
    @Override
    protected String getRelationTablePkField(Map metaMap) {
        return "code";
    }
}
