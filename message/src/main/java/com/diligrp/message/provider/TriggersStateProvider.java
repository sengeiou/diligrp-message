package com.diligrp.message.provider;

import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import com.diligrp.message.common.enums.TriggersEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/2 15:40
 */
@Component
public class TriggersStateProvider implements ValueProvider {

    /**
     * 取下拉列表的选项
     *
     * @param val       值对象
     * @param metaMap   meta信息
     * @param fieldMeta
     * @return
     */
    @Override
    public List<ValuePair<?>> getLookupList(Object val, Map metaMap, FieldMeta fieldMeta) {
        return Stream.of(TriggersEnum.EnabledStateEnum.values())
                .map(e -> new ValuePairImpl<>(e.getName(), String.valueOf(e.getCode())))
                .collect(Collectors.toList());
    }

    /**
     * 取显示文本的值
     *
     * @param object       值对象
     * @param metaMap   meta信息，包括:当前行数据:_rowData,当前字段名:_field及其它DTO中的meta信息
     * @param fieldMeta 当前字段的注解封装对象
     * @return
     */
    @Override
    public String getDisplayText(Object object, Map metaMap, FieldMeta fieldMeta) {
        if (Objects.isNull(object)) {
            return null;
        }
        TriggersEnum.EnabledStateEnum instance = TriggersEnum.EnabledStateEnum.getInstance(Integer.valueOf(object.toString()));
        if (Objects.nonNull(instance)) {
            return instance.getName();
        }
        return null;
    }
}
