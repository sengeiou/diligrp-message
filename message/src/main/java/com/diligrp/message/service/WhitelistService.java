package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.dili.ss.domain.EasyuiPageOutput;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.vo.WhitelistVo;

import java.util.Set;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 */
public interface WhitelistService extends BaseService<Whitelist, Long> {

    /**
     * 查询短信发送记录
     * @param whitelistVo 查询vo
     * @param useProvider 是否使用provider解析
     * @throws Exception
     * @return
     * */
    EasyuiPageOutput findByWhitelistVo(WhitelistVo whitelistVo, boolean useProvider) throws Exception;

    /**
     * 验证白名单用户时间是否包含在已有时间区间
     * @param whitelist 新增对象
     * @return false -- 未包含在已有时间区间； true --- 包含在已有时间区间内
     * */
    boolean checkDate(Whitelist whitelist);

    /**
     * 查询某个手机号，在某市场的此时此刻，是否在白名单中
     * @param marketCode
     * @return
     */
    Set<String> queryValidByMarketCode(String marketCode);

    /**
     * 更改白名单的状态信息
     * @param whitelist
     * @return
     */
    Integer updateWhitelistStatus(Whitelist whitelist);

    /**
     * 保存白名单信息
     * @param whitelist
     * @return
     */
    Integer saveWhitelist(Whitelist whitelist);
}