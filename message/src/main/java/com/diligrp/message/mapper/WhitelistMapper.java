package com.diligrp.message.mapper;

import com.dili.ss.base.MyMapper;
import com.diligrp.message.domain.Whitelist;

/**
 * @author yuehongbo
 */
public interface WhitelistMapper extends MyMapper<Whitelist> {

    /**
     * 查询某个手机号，在某市场的此时此刻，是否在白名单中
     * @param whitelist
     * @return
     */
    Integer queryValidByMarketCode(Whitelist whitelist);
}