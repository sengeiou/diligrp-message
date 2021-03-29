package com.diligrp.message.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.EasyuiPageOutput;
import com.diligrp.message.common.enums.BizNumberTypeEnum;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.SendLogVo;
import com.diligrp.message.mapper.SendLogMapper;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.remote.MarketRpcService;
import com.diligrp.message.service.remote.UidRpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-03 15:57:14.
 * @author yuehongbo
 */
@RequiredArgsConstructor
@Service
public class SendLogServiceImpl extends BaseServiceImpl<SendLog, Long> implements SendLogService {

    public SendLogMapper getActualMapper() {
        return (SendLogMapper)getDao();
    }

    private final MarketRpcService marketRpcService;
    private final UidRpcService uidRpcService;

    /**
     * 保存发送信息
     *
     * @param sendLog
     */
    @Override
    public void save(SendLog sendLog) {
        if (StrUtil.isBlank(sendLog.getRequestCode())) {
            Optional<String> bizNumber = uidRpcService.getBizNumber(BizNumberTypeEnum.SEND_REQUEST);
            sendLog.setRequestCode(bizNumber.orElse(IdUtil.getSnowflake(1, 1).nextIdStr()));
        }
        this.insertSelective(sendLog);
    }

    @Override
    public EasyuiPageOutput findBySendLogVo(SendLogVo sendLogVo, boolean useProvider) throws Exception{
        sendLogVo.setAuthMarkets(marketRpcService.getCurrentUserFirmCodes());
        return listEasyuiPageByExample(sendLogVo, useProvider);
    }
}