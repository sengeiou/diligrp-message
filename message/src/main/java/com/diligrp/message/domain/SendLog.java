package com.diligrp.message.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.dili.ss.domain.BaseDomain;
import com.dili.ss.domain.annotation.FindInSet;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2019-04-03 15:57:13.
 * @author yuehongbo
 */
@Table(name = "`message_send_log`")
@Getter
@Setter
public class SendLog extends BaseDomain {
    /**
     * ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 其它系统的此次消息请求码
     */
    @Column(name = "`request_code`")
    private String requestCode;

    /**
     * 来源市场
     */
    @Column(name = "`market_code`")
    private String marketCode;

    /**
     * 来源系统
     */
    @Column(name = "`system_code`")
    private String systemCode;

    /**
     * 应用场景
     */
    @Column(name = "`scene_code`")
    private String sceneCode;

    /**
     * 电话号码,多个以英文逗号隔开
     */
    @Column(name = "`cellphone`")
    @FindInSet
    private String cellphone;

    /**
     * 请求来源IP
     */
    @Column(name = "`remote_ip`")
    private String remoteIp;

    /**
     * 接收时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`receipt_time`")
    private LocalDateTime receiptTime;

    /**
     * 请求参数
     */
    @Column(name = "`parameters`")
    private String parameters;

    /**
     * 指定发送模板编码
     */
    @Column(name = "`template_code`")
    private String templateCode;

    /**
     * 消息内容
     */
    @Column(name = "`content`")
    private String content;

    /**
     * 发送状态
     */
    @Column(name = "`send_state`")
    private Integer sendState;

    /**
     * 发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`send_time`")
    private LocalDateTime sendTime;

    /**
     * 发送通道
     */
    @Column(name = "`send_channel`")
    private String sendChannel;

    /**
     * 请求第三方系统时返回的业务ID
     */
    @Column(name = "`request_id`")
    private String requestId;

    /**
     * 回执ID
     */
    @Column(name = "`biz_Id`")
    private String bizId;

    /**
     * 备注
     */
    @Column(name = "`remarks`")
    private String remarks;

}