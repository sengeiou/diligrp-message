package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.diligrp.message.common.enums.BizNumberTypeEnum;
import com.diligrp.message.domain.SequenceNumber;
import com.diligrp.message.mapper.SequenceNumberMapper;
import com.diligrp.message.service.SequenceNumberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 16:50
 */
@Service
public class SequenceNumberServiceImpl extends BaseServiceImpl<SequenceNumber, Long> implements SequenceNumberService {

    private SequenceNumberMapper getActualMapper() {
        return (SequenceNumberMapper)getDao();
    }

    @Override
    public String getBizNumberByType(BizNumberTypeEnum bizNumberType) {
        return getBizNumberByType(bizNumberType, null, false);
    }

    @Override
    public String getBizNumberByType(BizNumberTypeEnum bizNumberType, String prefix, Boolean addFirst) {
        if (bizNumberType == null) {
            return null;
        }
        if (StringUtils.isNotBlank(prefix)) {
            if (addFirst) {
                prefix = prefix + bizNumberType.getPrefix();
            } else {
                prefix = bizNumberType.getPrefix() + prefix;
            }
        } else {
            prefix = bizNumberType.getPrefix();
        }

        return getBizNumberByType(bizNumberType.getType(), bizNumberType.getDbFormat(), bizNumberType.getLength(), prefix);
    }

    @Override
    @Retryable(value = Exception.class, maxAttempts = 2)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String getBizNumberByType(String type, String dbFormat, Integer length, String prefix) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("type", type);
        map.put("format", dbFormat);
        map.put("length", length);
        getActualMapper().getSequence(map);
        String number = map.get("result") != null ? map.get("result").toString() : "";
        return prefix + number;
    }
}
