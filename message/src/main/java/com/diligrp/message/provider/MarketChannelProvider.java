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

@Component
public class MarketChannelProvider implements ValueProvider {
    private static final List<ValuePair<?>> BUFFER = new ArrayList<>();

    static {
        BUFFER.addAll(Stream.of(MessageEnum.ChannelEnum.values())
                .map(e -> new ValuePairImpl<>(e.getName(), e.getCode()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<ValuePair<?>> getLookupList(Object o, Map map, FieldMeta fieldMeta) {
        return BUFFER;
    }

    @Override
    public String getDisplayText(Object o, Map map, FieldMeta fieldMeta) {
        if (null == o) {
            return null;
        }
        if (o.toString().contains(",")){
            String[] channellist = o.toString().split(",");
            String buf = "";
            for (String str : channellist){
                ValuePair<?> valuePair = BUFFER.stream().filter(val -> str.equals(val.getValue())).findFirst().orElseGet(null);
                if (null != valuePair && !buf.contains(valuePair.getText())) {
                    buf += valuePair.getText()+ ",";
                }
            }
            return buf.substring(0, buf.length()-1);
        }else {
            ValuePair<?> valuePair = BUFFER.stream().filter(val -> o.equals(val.getValue())).findFirst().orElseGet(null);
            if (null != valuePair) {
               return valuePair.getText();
            }
        }
        return null;
    }
}
