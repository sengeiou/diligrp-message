package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.diligrp.message.domain.MarketChannel;

import java.util.Map;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-09 15:57:04.
 * @author yuehongbo
 */
public interface MarketChannelService extends BaseService<MarketChannel, Long> {

    /**
     * 根据市场编码获取该市场配置的通道信息
     * @param market 市场编码
     * @return 返回map结构数据，key 为数据ID，value 为通道数据本身
     */
    Map<Long,MarketChannel> queryByMarket(String market);
}