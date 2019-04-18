package com.diligrp.message.mapper;

import com.dili.ss.base.MyMapper;
import com.diligrp.message.domain.TriggersTemplate;

import java.util.List;

public interface TriggersTemplateMapper extends MyMapper<TriggersTemplate> {

    List<TriggersTemplate> selectByMarketChannelId(String marketChannelId);

    /**
     * 根据触发code查询模板信息
     * @param triggerCode
     * @return
     */
    List<TriggersTemplate> selectByTriggerCode(String triggerCode);
}