package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.dili.ss.domain.EasyuiPageOutput;
import com.diligrp.message.domain.Blacklist;
import com.diligrp.message.domain.query.BlacklistQuery;

import java.util.Set;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 */
public interface BlacklistService extends BaseService<Blacklist, Long> {

    /**
     * 查询黑名单信息
     * @param blacklistQuery 查询
     * @param useProvider 是否使用provider解析
     * @throws Exception
     * @return
     * */
    EasyuiPageOutput listPageForEasyui(BlacklistQuery blacklistQuery, boolean useProvider) throws Exception;

    /**
     * 验证黑名单用户时间是否包含在已有时间区间
     * @param blacklist 新增对象
     * @return false -- 未包含在已有时间区间； true --- 包含在已有时间区间内
     * */
    boolean checkDate(Blacklist blacklist);

    /**
     * 查询某个手机号，在某市场的此时此刻，是否在黑名单中
     * @param marketCode 市场编码
     * @return
     */
    Set<String> queryValidByMarketCode(String marketCode);

    /**
     * 更改黑名单的状态信息
     * @param blacklist
     * @return
     */
    Integer updateBlacklistStatus(Blacklist blacklist);

    /**
     * 保存黑名单信息
     * @param blacklist
     * @return
     */
    Integer saveBlacklist(Blacklist blacklist);
}