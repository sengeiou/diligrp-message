package com.diligrp.message.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.dili.ss.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2019-04-09 15:57:04.
 */
@Getter
@Setter
@Table(name = "`message_market_channel`")
public class MarketChannel extends BaseDomain {
    /**
     * ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 市场编码
     */
    @Column(name = "`market_code`")
    private String marketCode;

    /**
     * 通道编码
     */
    @Column(name = "`channel`")
    private String channel;

    /**
     * 通道签名
     */
    @Column(name = "`signature`")
    private String signature;

    /**
     * 企业名称
     */
    @Column(name = "`company_name`")
    private String companyName;

    /**
     * 通道账号
     */
    @Column(name = "`access_key`")
    private String accessKey;

    /**
     * 通道密码
     */
    @Column(name = "`secret`")
    private String secret;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`created`")
    private LocalDateTime created;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`modified`")
    private LocalDateTime modified;

}