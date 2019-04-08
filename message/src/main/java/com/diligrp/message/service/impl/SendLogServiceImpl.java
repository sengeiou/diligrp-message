package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.EasyuiPageOutput;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.SendLogVo;
import com.diligrp.message.mapper.SendLogMapper;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.remote.FirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-03 15:57:14.
 */
@Service
public class SendLogServiceImpl extends BaseServiceImpl<SendLog, Long> implements SendLogService {
    @Autowired
    FirmService firmService;

    public SendLogMapper getActualDao() {
        return (SendLogMapper)getDao();
    }

    @Override
    public EasyuiPageOutput findBySendLogVo(SendLogVo sendLogVo, boolean useProvider) throws Exception{
        sendLogVo.setAuthMarkets(firmService.getCurrentUserFirmCodes());
        return listEasyuiPageByExample(sendLogVo, useProvider);
    }
}