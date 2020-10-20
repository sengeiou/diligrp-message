package com.diligrp.message.service.impl;

import com.diligrp.message.domain.PushLog;
import com.diligrp.message.mapper.PushLogMapper;
import com.diligrp.message.service.PushLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2020-10-10 16:41:27.
 */
@RequiredArgsConstructor
@Service
public class PushLogServiceImpl extends CustomBaseServiceImpl<PushLog, Long> implements PushLogService {

    public PushLogMapper getActualDao() {
        return (PushLogMapper)getDao();
    }
}