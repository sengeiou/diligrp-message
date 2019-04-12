package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.mapper.TriggersTemplateMapper;
import com.diligrp.message.service.TriggersTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:53:25.
 */
@Service
public class TriggersTemplateServiceImpl extends BaseServiceImpl<TriggersTemplate, Long> implements TriggersTemplateService {

    public TriggersTemplateMapper getActualMapper() {
        return (TriggersTemplateMapper)getDao();
    }

    @Override
    public List<TriggersTemplate> listByMarketChannelId(String marketChannelId) {
        return this.getActualMapper().selectByMarketChannelId(marketChannelId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByTriggerCode(String triggerCode) {
        TriggersTemplate template = new TriggersTemplate();
        template.setTemplateCode(triggerCode);
        return getActualMapper().delete(template);
    }
}