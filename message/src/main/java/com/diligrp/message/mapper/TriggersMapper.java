package com.diligrp.message.mapper;

import com.dili.ss.base.MyMapper;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.vo.TriggersVo;

import java.util.List;

public interface TriggersMapper extends MyMapper<Triggers> {

    /**
     * 根据条件联合查询数据信息
     * @param triggers 触发点信息
     * @return
     */
    List<TriggersVo> selectForPage(Triggers triggers);
}