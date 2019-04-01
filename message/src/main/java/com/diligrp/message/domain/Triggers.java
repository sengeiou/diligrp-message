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
 * This file was generated on 2019-03-31 10:52:30.
 */
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
    private Boolean whitelist;

    /**
     * 是否启用
     */
    @Column(name = "`enabled`")
    private Byte enabled;

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
     * 获取触发点编码
     *
     * @return trigger_code - 触发点编码
     */
    @FieldDef(label="触发点编码", maxLength = 50)
    @EditMode(editor = FieldEditor.Text, required = true)
    public String getTriggerCode() {
        return triggerCode;
    }

    /**
     * 设置触发点编码
     *
     * @param triggerCode 触发点编码
     */
    public void setTriggerCode(String triggerCode) {
        this.triggerCode = triggerCode;
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
     * 获取所属系统
     *
     * @return system_code - 所属系统
     */
    @FieldDef(label="所属系统", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * 设置所属系统
     *
     * @param systemCode 所属系统
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * 获取应用场景
     *
     * @return scene_code - 应用场景
     */
    @FieldDef(label="应用场景", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getSceneCode() {
        return sceneCode;
    }

    /**
     * 设置应用场景
     *
     * @param sceneCode 应用场景
     */
    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }

    /**
     * 获取是否启用白名单
     *
     * @return whitelist - 是否启用白名单
     */
    @FieldDef(label="是否启用白名单")
    @EditMode(editor = FieldEditor.Text, required = false)
    public Boolean getWhitelist() {
        return whitelist;
    }

    /**
     * 设置是否启用白名单
     *
     * @param whitelist 是否启用白名单
     */
    public void setWhitelist(Boolean whitelist) {
        this.whitelist = whitelist;
    }

    /**
     * 获取是否启用
     *
     * @return enabled - 是否启用
     */
    @FieldDef(label="是否启用")
    @EditMode(editor = FieldEditor.Text, required = false)
    public Byte getEnabled() {
        return enabled;
    }

    /**
     * 设置是否启用
     *
     * @param enabled 是否启用
     */
    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    @FieldDef(label="创建时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
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
    @EditMode(editor = FieldEditor.Datetime, required = false)
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