package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.diligrp.message.domain.TriggersTemplate;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:53:25.
 */
public interface TriggersTemplateService extends BaseService<TriggersTemplate, Long> {

    /**
     * 通过市场通道id查询模板 -- 用于删除验证
     * @param marketChannelId  市场通道ID,类型String
     * @return List
     * */
     List<TriggersTemplate> listByMarketChannelId(String marketChannelId);

    /**
     * 根据场景触发带你删除
     * @param triggerCode
     * @return
     */
     Integer deleteByTriggerCode(String triggerCode);

    /**
     * 根据触发code查询模板信息
     * @param triggerCode
     * @return
     */
    List<TriggersTemplate> selectByTriggerCode(String triggerCode);
}