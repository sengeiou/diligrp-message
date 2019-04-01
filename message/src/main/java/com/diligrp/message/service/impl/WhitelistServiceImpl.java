package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.mapper.WhitelistMapper;
import com.diligrp.message.service.WhitelistService;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 */
@Service
public class WhitelistServiceImpl extends BaseServiceImpl<Whitelist, Long> implements WhitelistService {

    public WhitelistMapper getActualDao() {
        return (WhitelistMapper)getDao();
    }
}