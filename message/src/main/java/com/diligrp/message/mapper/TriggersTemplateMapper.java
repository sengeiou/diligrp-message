package com.diligrp.message.mapper;

import com.dili.ss.base.MyMapper;
import com.diligrp.message.domain.TriggersTemplate;

import java.util.List;

public interface TriggersTemplateMapper extends MyMapper<TriggersTemplate> {

    List<TriggersTemplate> selectByMarketChannelId(String marketChannelId);
}