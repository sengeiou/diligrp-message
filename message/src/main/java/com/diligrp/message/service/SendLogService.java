package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.dili.ss.domain.EasyuiPageOutput;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.SendLogVo;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-03 15:57:14.
 */
public interface SendLogService extends BaseService<SendLog, Long> {

    /**
     * 查询短信发送记录
     * @param sendLogVo 查询vo
     * @param useProvider 是否使用provider解析
     * */
    EasyuiPageOutput findBySendLogVo(SendLogVo sendLogVo, boolean useProvider)throws Exception;
}