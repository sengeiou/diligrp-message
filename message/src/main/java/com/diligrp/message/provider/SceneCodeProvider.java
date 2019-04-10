package com.diligrp.message.provider;

import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SceneCodeProvider implements ValueProvider {


    @Override
    public List<ValuePair<?>> getLookupList(Object o, Map map, FieldMeta fieldMeta) {
        return null;
    }

    @Override
    public String getDisplayText(Object o, Map map, FieldMeta fieldMeta){
        Map rowData = (Map)map.get(ValueProvider.ROW_DATA_KEY);
        if(rowData.get("$_sceneCode") == null){
            return null;
        }
        try {
            return rowData.get("$_sceneCode").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}