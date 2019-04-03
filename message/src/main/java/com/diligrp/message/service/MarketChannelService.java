package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.dili.ss.domain.EasyuiPageOutput;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.vo.MarketChannelVo;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:36:28.
 */
public interface MarketChannelService extends BaseService<MarketChannel, Long> {
    /**
     * 查询市场通道数据信息
     * @param marketChannel 查询用于 拥有权限 的市场的通道信息
     * @return
     */
    EasyuiPageOutput listAll(MarketChannelVo marketChannel) throws Exception;
}