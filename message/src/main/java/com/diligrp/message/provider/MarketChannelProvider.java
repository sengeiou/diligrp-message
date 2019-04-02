package com.diligrp.message.provider;

import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import com.diligrp.message.common.enums.MarketChannelEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MarketChannelProvider implements ValueProvider {
    private static final List<ValuePair<?>> BUFFER = new ArrayList<>();

    static {
        BUFFER.addAll(Stream.of(MarketChannelEnum.ChannelEnum.values())
                .map(e -> new ValuePairImpl<>(e.getName(), e.getCode()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<ValuePair<?>> getLookupList(Object o, Map map, FieldMeta fieldMeta) {
        return null;
    }

    @Override
    public String getDisplayText(Object o, Map map, FieldMeta fieldMeta) {
        return null;
    }
}
