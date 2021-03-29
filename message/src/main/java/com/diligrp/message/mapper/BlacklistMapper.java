package com.diligrp.message.mapper;

import com.dili.ss.base.MyMapper;
import com.diligrp.message.domain.Blacklist;

import java.util.Set;

/**
 * @author yuehongbo
 */
public interface BlacklistMapper extends MyMapper<Blacklist> {

    /**
     * 查询某个手机号，在某市场的此时此刻，是否在黑名单中
     * @param marketCode 市场编码
     * @return
     */
    Set<String> queryValidByMarketCode(String  marketCode);
}