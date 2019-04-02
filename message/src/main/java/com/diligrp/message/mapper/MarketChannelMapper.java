package com.diligrp.message.mapper;

import com.dili.ss.base.MyMapper;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.vo.MarketChannelVo;

import java.util.List;

public interface MarketChannelMapper extends MyMapper<MarketChannel> {

    List<MarketChannelVo> listAll(MarketChannel marketChannel);
}