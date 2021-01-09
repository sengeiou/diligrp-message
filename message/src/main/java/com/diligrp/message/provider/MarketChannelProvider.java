package com.diligrp.message.provider;

import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import com.diligrp.message.common.enums.MessageEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yuehongbo
 */
@Component
public class MarketChannelProvider implements ValueProvider {


    @Override
    public List<ValuePair<?>> getLookupList(Object o, Map map, FieldMeta fieldMeta) {
        return Stream.of(MessageEnum.ChannelEnum.values())
                .map(e -> new ValuePairImpl<>(e.getName(), e.getCode()))
                .collect(Collectors.toList());
    }

    @Override
    public String getDisplayText(Object o, Map map, FieldMeta fieldMeta){
        if (Objects.isNull(o)) {
            return null;
        }
        MessageEnum.ChannelEnum channelEnum = MessageEnum.ChannelEnum.getInstance(o.toString());
        if (Objects.nonNull(channelEnum)) {
            return channelEnum.getName();
        }
        return null;
    }
}
