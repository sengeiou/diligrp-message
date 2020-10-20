package com.diligrp.message.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.dili.ss.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2020-10-10 16:41:27.
 */
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "`message_push_log`")
public class PushLog extends BaseDomain {
    /**
     * ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Accessors(chain = false)
    private Long id;

    /**
     * 此次推送的请求批次码
     */
    @Column(name = "`request_code`")
    private String requestCode;

    /**
     * 所属市场ID
     */
    @Column(name = "`market_id`")
    private Long marketId;

    /**
     * 推送用户ID
     */
    @Column(name = "`user_id`")
    private Long userId;

    /**
     * 设备注册ID
     */
    @Column(name = "`registration_id`")
    private String registrationId;

    /**
     * 推送平台:android,ios 等
     */
    @Column(name = "`platform`")
    private String platform;

    /**
     * 通知内容
     */
    @Column(name = "`alert`")
    private String alert;

    /**
     * 通知标题
     */
    @Column(name = "`title`")
    private String title;

    /**
     * 消息扩展内容--json格式
     */
    @Column(name = "`extras`")
    private String extras;

    /**
     * 消息接收时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`receipt_time`")
    private LocalDateTime receiptTime;

    /**
     * 消息推送状态
     */
    @Column(name = "`push_state`")
    private Integer pushState;

    /**
     * 消息推送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`push_time`")
    private LocalDateTime pushTime;

    /**
     * 推送通道
     */
    @Column(name = "`push_channel`")
    private Integer pushChannel;

    /**
     * 第三方消息ID
     */
    @Column(name = "`message_id`")
    private String messageId;

    /**
     * 第三方发送编码
     */
    @Column(name = "`send_no`")
    private String sendNo;

    /**
     * 失败编码
     */
    @Column(name = "`failure_code`")
    private String failureCode;

    /**
     * 失败信息
     */
    @Column(name = "`failure_message`")
    private String failureMessage;

}