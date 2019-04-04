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
    @Column(name = "`market_code`")
    private List<String> authMarkets;

    /**
     * 发送时间  -- 开始
     */
    private Date startSendTime;

    /**
     * 发送时间 -- 结束
     */
    private Date endSendTime;

    @Operator(Operator.IN)

    public List<String> getAuthMarkets() {
        return authMarkets;
    }

    public void setAuthMarkets(List<String> authMarkets) {
        this.authMarkets = authMarkets;
    }

    @Column(name = "`send_time`")
    @Operator(Operator.GREAT_EQUAL_THAN)
    public Date getStartSendTime() {
        return startSendTime;
    }

    public void setStartSendTime(Date startSendTime) {
        this.startSendTime = startSendTime;
    }

    @Column(name = "`send_time`")
    @Operator(Operator.LITTLE_EQUAL_THAN)
    public Date getEndSendTime() {
        return endSendTime;
    }

    public void setEndSendTime(Date endSendTime) {
        this.endSendTime = endSendTime;
    }


}
