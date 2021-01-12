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
 * This file was generated on 2019-04-02 10:16:25.
 */
@Getter
@Setter
@Table(name = "`message_triggers`")
public class Triggers extends BaseDomain {
    /**
     * ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 触发点编码
     */
    @Column(name = "`trigger_code`")
    private String triggerCode;

    /**
     * 市场编码
     */
    @Column(name = "`market_code`")
    private String marketCode;

    /**
     * 所属系统
     */
    @Column(name = "`system_code`")
    private String systemCode;

    /**
     * 应用场景
     */
    @Column(name = "`scene_code`")
    private String sceneCode;

    /**
     * 是否启用白名单
     */
    @Column(name = "`whitelist`")
    private Integer whitelist;

    /**
     * 是否启用
     */
    @Column(name = "`enabled`")
    private Integer enabled;

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