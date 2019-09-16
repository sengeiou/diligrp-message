package com.diligrp.message.domain;

import com.dili.ss.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
     * 开始日期
     */
    @Column(name = "`start_date`")
    private Date startDate;

    /**
     * 结束日期
     */
    @Column(name = "`end_date`")
    private Date endDate;

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
    @Column(name = "`created`")
    private Date created;

    /**
     * 修改时间
     */
    @Column(name = "`modified`")
    private Date modified;

}