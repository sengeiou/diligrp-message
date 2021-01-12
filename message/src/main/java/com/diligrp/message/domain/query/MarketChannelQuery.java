package com.diligrp.message.domain.query;

import com.dili.ss.domain.annotation.Operator;
import com.diligrp.message.domain.MarketChannel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;
import java.util.Set;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2021/1/11 15:41
 */
@Getter
@Setter
public class MarketChannelQuery extends MarketChannel {

    /**
     * 权限市场
     * */
    @Operator(Operator.IN)
    @Column(name = "`market_code`")
    private List<String> authMarkets;

    /**
     * 权限市场
     * */
    @Operator(Operator.IN)
    @Column(name = "`id`")
    private Set<Long> idSet;
}
