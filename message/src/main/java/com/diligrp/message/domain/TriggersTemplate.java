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
 * This file was generated on 2019-04-02 10:22:23.
 * @author yuehongbo
 */
@Getter
@Setter
@Table(name = "`message_triggers_template`")
public class TriggersTemplate extends BaseDomain {
    /**
     * ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 消息触发点
     */
    @Column(name = "`trigger_code`")
    private String triggerCode;

    /**
     * 市场通道ID(如果有多个，用#号隔开)
     */
    @Column(name = "`market_channel_ids`")
    private String marketChannelIds;

    /**
     * 模板通道
     */
    @Column(name = "`channel`")
    private String channel;

    /**
     * 模板名称
     */
    @Column(name = "`template_name`")
    private String templateName;

    /**
     * 模板编码
     */
    @Column(name = "`template_code`")
    private String templateCode;

    /**
     * 模板内容
     */
    @Column(name = "`template_content`")
    private String templateContent;

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