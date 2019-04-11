package com.diligrp.message.domain;

import com.dili.ss.domain.BaseDomain;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;
import java.util.Date;
import javax.persistence.*;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2019-04-02 10:14:09.
 */
@Table(name = "`message_whitelist`")
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

    /**
     * 获取ID
     *
     * @return id - ID
     */
    @FieldDef(label="ID")
    @EditMode(editor = FieldEditor.Number, required = true)
    @Override
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取所属市场
     *
     * @return market_code - 所属市场
     */
    @FieldDef(label="所属市场", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = true)
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * 设置所属市场
     *
     * @param marketCode 所属市场
     */
    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    /**
     * 获取客户姓名
     *
     * @return customer_name - 客户姓名
     */
    @FieldDef(label="客户姓名", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = true)
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置客户姓名
     *
     * @param customerName 客户姓名
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 获取客户手机号
     *
     * @return cellphone - 客户手机号
     */
    @FieldDef(label="客户手机号", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = true)
    public String getCellphone() {
        return cellphone;
    }

    /**
     * 设置客户手机号
     *
     * @param cellphone 客户手机号
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * 获取开始日期
     *
     * @return start_date - 开始日期
     */
    @FieldDef(label="开始日期")
    @EditMode(editor = FieldEditor.Datetime, required = true)
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置开始日期
     *
     * @param startDate 开始日期
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取结束日期
     *
     * @return end_date - 结束日期
     */
    @FieldDef(label="结束日期")
    @EditMode(editor = FieldEditor.Datetime, required = true)
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置结束日期
     *
     * @param endDate 结束日期
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取信息来源(系统、手动添加?具体参考枚举定义)
     *
     * @return source - 信息来源(系统、手动添加?具体参考枚举定义)
     */
    @FieldDef(label="信息来源(系统、手动添加?具体参考枚举定义)", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getSource() {
        return source;
    }

    /**
     * 设置信息来源(系统、手动添加?具体参考枚举定义)
     *
     * @param source 信息来源(系统、手动添加?具体参考枚举定义)
     */
    public void setSource(String source) {
        this.source = source;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 获取是否删除(1-是;0-否)
     *
     * @return deleted - 是否删除(1-是;0-否)
     */
    @FieldDef(label="是否删除(1-是;0-否)")
    @EditMode(editor = FieldEditor.Number, required = false)
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否删除(1-是;0-否)
     *
     * @param deleted 是否删除(1-是;0-否)
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    @FieldDef(label="创建时间")
    @EditMode(editor = FieldEditor.Datetime, required = true)
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 获取修改时间
     *
     * @return modified - 修改时间
     */
    @FieldDef(label="修改时间")
    @EditMode(editor = FieldEditor.Datetime, required = true)
    public Date getModified() {
        return modified;
    }

    /**
     * 设置修改时间
     *
     * @param modified 修改时间
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }
}