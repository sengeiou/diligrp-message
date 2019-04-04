package com.diligrp.message.mapper;

import com.dili.ss.base.MyMapper;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.SendLogVo;

import java.util.List;

public interface SendLogMapper extends MyMapper<SendLog> {
    /**
     * 根据用户输入查询所有发送记录日志
     * @param vo
     * @return
     */
    List<SendLog> findBySendLogVo(SendLogVo vo);
}