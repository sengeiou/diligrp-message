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
 * This file was generated on 2019-04-02 10:22:23.
 */
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
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取消息触发点
     *
     * @return trigger_code - 消息触发点
     */
    @FieldDef(label="消息触发点", maxLength = 50)
    @EditMode(editor = FieldEditor.Text, required = true)
    public String getTriggerCode() {
        return triggerCode;
    }

    /**
     * 设置消息触发点
     *
     * @param triggerCode 消息触发点
     */
    public void setTriggerCode(String triggerCode) {
        this.triggerCode = triggerCode;
    }

    /**
     * 获取模板通道
     *
     * @return channel - 模板通道
     */
    @FieldDef(label="模板通道", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getChannel() {
        return channel;
    }

    /**
     * 设置模板通道
     *
     * @param channel 模板通道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取模板名称
     *
     * @return template_name - 模板名称
     */
    @FieldDef(label="模板名称", maxLength = 50)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getTemplateName() {
        return templateName;
    }

    /**
     * 设置模板名称
     *
     * @param templateName 模板名称
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * 获取模板编码
     *
     * @return template_code - 模板编码
     */
    @FieldDef(label="模板编码", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getTemplateCode() {
        return templateCode;
    }

    /**
     * 设置模板编码
     *
     * @param templateCode 模板编码
     */
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    /**
     * 获取模板内容
     *
     * @return template_content - 模板内容
     */
    @FieldDef(label="模板内容", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getTemplateContent() {
        return templateContent;
    }

    /**
     * 设置模板内容
     *
     * @param templateContent 模板内容
     */
    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
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