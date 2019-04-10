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
 * This file was generated on 2019-04-09 15:57:04.
 */
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
     * 获取市场编码
     *
     * @return market_code - 市场编码
     */
    @FieldDef(label="市场编码", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = true)
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * 设置市场编码
     *
     * @param marketCode 市场编码
     */
    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    /**
     * 获取通道编码
     *
     * @return channel - 通道编码
     */
    @FieldDef(label="通道编码", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getChannel() {
        return channel;
    }

    /**
     * 设置通道编码
     *
     * @param channel 通道编码
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取通道签名
     *
     * @return signature - 通道签名
     */
    @FieldDef(label="通道签名", maxLength = 50)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getSignature() {
        return signature;
    }

    /**
     * 设置通道签名
     *
     * @param signature 通道签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取通道账号
     *
     * @return access_key - 通道账号
     */
    @FieldDef(label="通道账号", maxLength = 50)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * 设置通道账号
     *
     * @param accessKey 通道账号
     */
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    /**
     * 获取通道密码
     *
     * @return secret - 通道密码
     */
    @FieldDef(label="通道密码", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getSecret() {
        return secret;
    }

    /**
     * 设置通道密码
     *
     * @param secret 通道密码
     */
    public void setSecret(String secret) {
        this.secret = secret;
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