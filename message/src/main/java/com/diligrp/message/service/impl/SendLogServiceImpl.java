package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.metadata.ValueProviderUtils;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.SendLogVo;
import com.diligrp.message.mapper.SendLogMapper;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.remote.FirmService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
//        if (sendLogVo.getRows() != null && sendLogVo.getRows().intValue() >= 1) {
//            PageHelper.startPage(sendLogVo.getPage().intValue(), sendLogVo.getRows().intValue());
//        }
        sendLogVo.setAuthMarkets(firmService.getCurrentUserFirmCodes());
//        List list = this.getActualDao().findBySendLogVo(sendLogVo);
//        long total = list instanceof Page ? ((Page) list).getTotal() : (long) list.size();
//        List results = useProvider ? ValueProviderUtils.buildDataByProvider(sendLogVo, list) : list;
//        return new EasyuiPageOutput(Integer.valueOf(Integer.parseInt(String.valueOf(total))), results);
        return listEasyuiPageByExample(sendLogVo, true);
    }
}