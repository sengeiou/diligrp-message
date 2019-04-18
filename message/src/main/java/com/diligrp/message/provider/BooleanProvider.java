package com.diligrp.message.provider;

import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import com.diligrp.message.common.enums.MessageEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yuehongbo
 */
@Component
public class BooleanProvider implements ValueProvider {
    private static final List<ValuePair<?>> BUFFER = new ArrayList<>();

    static {
        BUFFER.addAll(Stream.of(MessageEnum.BooleanEnum.values())
                .map(e -> new ValuePairImpl<>(e.getName(), e.getCode().toString()))
                .collect(Collectors.toList()));
    }

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
        return BUFFER;
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
        if (null == object) {
            return null;
        }
        ValuePair<?> valuePair = BUFFER.stream().filter(val -> object.toString().equals(val.getValue().toString())).findFirst().orElseGet(null);
        if (null != valuePair) {
            return valuePair.getText();
        }
        return null;
    }
}
