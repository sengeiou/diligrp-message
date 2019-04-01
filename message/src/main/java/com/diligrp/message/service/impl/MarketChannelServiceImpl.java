package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.diligrp.message.mapper.MarketChannelMapper;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.service.MarketChannelService;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:36:28.
 */
@Service
public class MarketChannelServiceImpl extends BaseServiceImpl<MarketChannel, Long> implements MarketChannelService {

    public MarketChannelMapper getActualDao() {
        return (MarketChannelMapper)getDao();
    }
}