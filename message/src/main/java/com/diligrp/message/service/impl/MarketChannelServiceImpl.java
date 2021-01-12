package com.diligrp.message.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.dili.ss.base.BaseServiceImpl;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.query.MarketChannelQuery;
import com.diligrp.message.mapper.MarketChannelMapper;
import com.diligrp.message.service.MarketChannelService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-09 15:57:04.
 * @author yuehongbo
 */
@Service
public class MarketChannelServiceImpl extends BaseServiceImpl<MarketChannel, Long> implements MarketChannelService {

    public MarketChannelMapper getActualDao() {
        return (MarketChannelMapper)getDao();
    }

    /**
     * 根据市场编码获取该市场配置的通道信息
     *
     * @param market 市场编码
     * @return 返回map结构数据，key 为数据ID，value 为通道数据本身
     */
    @Override
    public Map<Long, MarketChannel> queryByMarket(String market) {
        if (StrUtil.isNotBlank(market)){
            MarketChannel marketChannel = new MarketChannel();
            marketChannel.setMarketCode(market);
            List<MarketChannel> marketChannels = this.list(marketChannel);
            if (CollectionUtil.isNotEmpty(marketChannels)){
                return marketChannels.stream().collect(Collectors.toMap(MarketChannel::getId, mc -> mc));
            }
        }
        return Collections.EMPTY_MAP;
    }

    @Override
    public List<MarketChannel> queryByIds(Set<Long> ids) {
        if (CollectionUtil.isNotEmpty(ids)) {
            MarketChannelQuery query = new MarketChannelQuery();
            query.setIdSet(ids);
            return this.listByExample(query);
        }
        return Collections.emptyList();
    }

}