package com.diligrp.message.domain.vo;

import com.dili.ss.domain.annotation.Operator;
import com.diligrp.message.domain.SendLog;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * @author yuehongbo
 */
@Getter
@Setter
public class SendLogVo extends SendLog {

    /**
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

}
