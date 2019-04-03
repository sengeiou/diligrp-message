package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.metadata.ValueProviderUtils;
import com.diligrp.message.domain.vo.MarketChannelVo;
import com.diligrp.message.mapper.MarketChannelMapper;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.service.MarketChannelService;
import com.diligrp.message.service.remote.FirmService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Stream;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:36:28.
 */
@Service
public class MarketChannelServiceImpl extends BaseServiceImpl<MarketChannel, Long> implements MarketChannelService {

    @Autowired
    FirmService firmService;

    public MarketChannelMapper getActualDao() {
        return (MarketChannelMapper)getDao();
    }

    @Override
    public EasyuiPageOutput listAll(MarketChannel marketChannel) throws Exception {
        marketChannel.setSort("id");
        marketChannel.setOrder("desc");
        marketChannel.setMetadata("authMarkets", firmService.getCurrentUserFirmCodes());
        List<MarketChannelVo> list = this.getActualDao().listAll(marketChannel);
        return new EasyuiPageOutput(Integer.parseInt(String.valueOf(list.size())), ValueProviderUtils.buildDataByProvider(marketChannel, list));
    }

}