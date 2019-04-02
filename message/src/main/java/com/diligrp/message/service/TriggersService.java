package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.dili.ss.domain.EasyuiPageOutput;
import com.diligrp.message.domain.Triggers;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 * @author yuehongbo
 */
public interface TriggersService extends BaseService<Triggers, Long> {

    /**
     * 根据实体查询easyui分页结果， 支持用metadata信息中字段对应的provider构建数据
     * @param triggers
     * @param useProvider 是否使用Provider
     * @exception Exception
     * @return
     */
    EasyuiPageOutput listPageForEasyui(Triggers triggers,boolean useProvider) throws Exception;
}