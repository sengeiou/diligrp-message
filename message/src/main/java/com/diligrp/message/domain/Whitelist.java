package com.diligrp.message.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.dili.ss.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2019-04-02 10:14:09.
 */
@Table(name = "`message_whitelist`")
@Getter
@Setter
public class Whitelist extends BaseDomain {
    /**
     * ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属市场
     */
    @Column(name = "`market_code`")
    private String marketCode;

    /**
     * 客户姓名
     */
    @Column(name = "`customer_name`")
    private String customerName;

    /**
     * 客户手机号
     */
    @Column(name = "`cellphone`")
    private String cellphone;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`start_date`")
    private LocalDateTime startDateTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`end_date`")
    private LocalDateTime endDateTime;

    /**
     * 信息来源(系统、手动添加?具体参考枚举定义)
     */
    @Column(name = "`source`")
    private String source;

    /**
     * 数据如果来源于系统，则记录对应系统中的数据id
     */
    @Column(name="`source_id`")
    private Long sourceId;

    /**
     * 白名单状态
     */
    @Column(name="`status`")
    private Integer status;

    /**
     * 是否删除(1-是;0-否)
     */
    @Column(name = "`deleted`")
    private Integer deleted;

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


    /**
     * 开始时间
     * 用于接收页面传入的对象
     */
    @Transient
    private LocalDate startDate;

    /**
     * 结束时间
     * 用于接收页面传入对象
     */
    @Transient
    private LocalDate endDate;

}