package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.mapper.TriggersMapper;
import com.diligrp.message.service.TriggersService;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 */
@Service
public class TriggersServiceImpl extends BaseServiceImpl<Triggers, Long> implements TriggersService {

    public TriggersMapper getActualDao() {
        return (TriggersMapper)getDao();
    }
}