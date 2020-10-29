package com.diligrp.message.provider;

import cn.hutool.core.collection.CollectionUtil;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.metadata.BatchProviderMeta;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.provider.BatchDisplayTextProviderSupport;
import com.dili.uap.sdk.domain.User;
import com.diligrp.message.service.remote.UapUserRpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/29 11:06
 */
@RequiredArgsConstructor
@Component
public class UapUserProvider extends BatchDisplayTextProviderSupport {

    private final UapUserRpcService uapUserRpcService;

    @Override
    public List<ValuePair<?>> getLookupList(Object obj, Map metaMap, FieldMeta fieldMeta) {
        List<User> list = uapUserRpcService.getCurrentMarketUser(obj.toString());
        List<ValuePair<?>> resultList = list.stream().map(f -> {
            return (ValuePair<?>) new ValuePairImpl(f.getRealName(), f.getId());
        }).collect(Collectors.toList());
        return resultList;
    }

    @Override
    protected BatchProviderMeta getBatchProviderMeta(Map metaMap) {
        BatchProviderMeta batchProviderMeta = DTOUtils.newInstance(BatchProviderMeta.class);
        //设置主DTO和关联DTO需要转义的字段名
        batchProviderMeta.setEscapeFiled("realName");
        //忽略大小写关联
        batchProviderMeta.setIgnoreCaseToRef(true);
        //关联(数据库)表的主键的字段名，默认取id
        batchProviderMeta.setRelationTablePkField("id");
        //当未匹配到数据时，返回的值
        batchProviderMeta.setMismatchHandler(t -> "-");
        return batchProviderMeta;
    }

    @Override
    protected List getFkList(List<String> relationIds, Map metaMap) {
        if (CollectionUtil.isEmpty(relationIds)) {
            return Collections.EMPTY_LIST;
        }
        relationIds = relationIds.stream().distinct().collect(Collectors.toList());
        return uapUserRpcService.listUserByIds(relationIds);
    }

    public String getDisplayText(Object obj, Map metaMap, FieldMeta fieldMeta) {
        if (Objects.nonNull(obj)) {
            Optional<User> byId = uapUserRpcService.getById(Long.valueOf(obj.toString()));
            return byId.isPresent() ? byId.get().getRealName() : "";
        }
        return "";
    }

}
