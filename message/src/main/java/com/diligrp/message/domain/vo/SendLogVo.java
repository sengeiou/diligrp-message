package com.diligrp.message.domain.vo;

import com.dili.ss.domain.annotation.Operator;
import com.diligrp.message.domain.SendLog;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

public class SendLogVo extends SendLog {
    /**
     *
     * 权限市场
     * */
    @Operator(Operator.IN)
    @Column(name = "`market_code`")
    private List<String> authMarkets;

    /**
     * 发送时间  -- 开始
     */
    @Column(name = "`send_time`")
    @Operator(Operator.GREAT_EQUAL_THAN)
    private Date startSendTime;

    /**
     * 发送时间 -- 结束
     */

    @Column(name = "`send_time`")
    @Operator(Operator.LITTLE_EQUAL_THAN)
    private Date endSendTime;


    public List<String> getAuthMarkets() {
        return authMarkets;
    }

    public void setAuthMarkets(List<String> authMarkets) {
        this.authMarkets = authMarkets;
    }

    public Date getStartSendTime() {
        return startSendTime;
    }

    public void setStartSendTime(Date startSendTime) {
        this.startSendTime = startSendTime;
    }

    public Date getEndSendTime() {
        return endSendTime;
    }

    public void setEndSendTime(Date endSendTime) {
        this.endSendTime = endSendTime;
    }


}
