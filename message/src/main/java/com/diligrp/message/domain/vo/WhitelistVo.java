package com.diligrp.message.domain.vo;

import com.dili.ss.domain.annotation.Operator;
import com.diligrp.message.domain.Whitelist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
public class WhitelistVo extends Whitelist {
    /**
     *
     * 权限市场
     * */
    @Operator(Operator.IN)
    @Column(name = "`market_code`")
    private List<String> authMarkets;

    /**
     * 关键字查询
     * @return
     */
    @Transient
    private String keywords;
}
