package com.diligrp.message.domain.vo;

import com.dili.ss.domain.annotation.Operator;
import com.diligrp.message.domain.MarketChannel;

import javax.persistence.Column;
import java.util.List;

public class MarketChannelVo extends MarketChannel {
    /**
     *
     * 权限市场
     * */
    @Operator(Operator.IN)
    @Column(name = "`market_code`")
    private List<String> authMarkets;

    public List<String> getAuthMarkets() {
        return authMarkets;
    }

    public void setAuthMarkets(List<String> authMarkets) {
        this.authMarkets = authMarkets;
    }
}
