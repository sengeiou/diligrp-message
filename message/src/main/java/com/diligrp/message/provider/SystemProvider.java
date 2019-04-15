package com.diligrp.message.provider;

import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.common.enums.TriggersEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/15 16:16
 */
@Component
public class SystemProvider implements ValueProvider {

    private static final List<ValuePair<?>> BUFFER = new ArrayList<>();

    static {
        BUFFER.addAll(Stream.of(MessageEnum.SystemEnum.values())
                .map(e -> new ValuePairImpl<>(e.getName(), e.getCode()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<ValuePair<?>> getLookupList(Object val, Map metaMap, FieldMeta fieldMeta) {
        return BUFFER;
    }

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
