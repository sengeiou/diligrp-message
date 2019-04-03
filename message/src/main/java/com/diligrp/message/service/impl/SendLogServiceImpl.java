package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.mapper.SendLogMapper;
import com.diligrp.message.service.SendLogService;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-03 15:57:14.
 */
@Service
public class SendLogServiceImpl extends BaseServiceImpl<SendLog, Long> implements SendLogService {

    public SendLogMapper getActualDao() {
        return (SendLogMapper)getDao();
    }
}