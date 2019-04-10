package com.diligrp.message.provider;

import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.utils.Base64Util;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PasswordProvider implements ValueProvider {


    @Override
    public List<ValuePair<?>> getLookupList(Object o, Map map, FieldMeta fieldMeta) {
        return null;
    }

    @Override
    public String getDisplayText(Object o, Map map, FieldMeta fieldMeta){
        MarketChannel marketChannel = (MarketChannel)map.get(ValueProvider.ROW_DATA_KEY);
        try {
            return Base64Util.getDecoderString(marketChannel.getSecret());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
