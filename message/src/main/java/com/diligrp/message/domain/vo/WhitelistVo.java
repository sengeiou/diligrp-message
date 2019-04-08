package com.diligrp.message.domain.vo;

import com.dili.ss.domain.annotation.Operator;
import com.diligrp.message.domain.Whitelist;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class WhitelistVo extends Whitelist {
    /**
     *
     * 权限市场
     * */
    @Operator(Operator.IN)
    @Column(name = "`market_code`")
    private List<String> authMarkets;

//    /**
//     * 发送时间  -- 开始
//     */
//    @Column(name = "`start_date`")
//    @Operator(Operator.GREAT_EQUAL_THAN)
//    private Date startTime;
//
//    /**
//     * 发送时间 -- 结束
//     */
//
//    @Column(name = "`endDate`")
//    @Operator(Operator.LITTLE_EQUAL_THAN)
//    private Date endTime;

    /**
     * 关键字查询
     * @return
     */
    @Transient
    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getAuthMarkets() {
        return authMarkets;
    }

    public void setAuthMarkets(List<String> authMarkets) {
        this.authMarkets = authMarkets;
    }
//
//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }
}
