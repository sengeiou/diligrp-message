package com.diligrp.message.domain.vo;

import com.diligrp.message.domain.SendLog;

import java.util.Date;
import java.util.List;

public class SendLogVo extends SendLog {
    /**
     *
     * 权限市场
     * */
    private List<String> authMarkets;

    /**
     * 发送时间  -- 开始
     */
    private Date startSendTime;

    /**
     * 发送时间 -- 结束
     */
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


    //    @Column(name = "`send_time`")
//    @Operator(Operator.GREAT_EQUAL_THAN)
//    Date getStartSendTime();
//    void setStartSendTime(Date startSendTime);
//
//    @Column(name = "`send_time`")
//    @Operator(Operator.LITTLE_EQUAL_THAN)
//    Date getEndSendTime();
//    void setEndSendTime(Date endSendTime);


}
