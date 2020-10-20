package com.diligrp.message.domain.query;

import com.alibaba.fastjson.annotation.JSONField;
import com.dili.commons.bstable.Tablepar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/13 14:41
 */
@Accessors(chain = true)
@Getter
@Setter
public class PushLogQuery extends Tablepar {

    /**
     * 此次推送的请求批次码
     */
    private String requestCode;

    /**
     * 所属市场ID
     */
    private Long marketId;

    /**
     * 设备注册ID
     */
    private String registrationId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 推送平台:android,ios 等
     */
    private String platform;

    /**
     * 通知内容
     */
    private String alert;

    /**
     * 消息推送状态
     */
    private Integer pushState;

    /**
     * 消息推送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pushTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pushTimeEnd;

    /**
     * 推送通道
     */
    @Column(name = "`push_channel`")
    private Integer pushChannel;

}
