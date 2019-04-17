package com.diligrp.message.domain;

import com.dili.ss.domain.BaseDomain;
import com.dili.ss.domain.annotation.FindInSet;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;

import javax.persistence.*;
import java.util.Date;

/**
 * 由MyBatis Generator工具自动生成
 * 
 * This file was generated on 2019-04-03 15:57:13.
 */
@Table(name = "`message_send_log`")
public class SendLog extends BaseDomain {
    /**
     * ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 此次消息请求码
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
     * 接收时间
     */
    @Column(name = "`receipt_time`")
    private Date receiptTime;

    /**
     * 请求参数
     */
    @Column(name = "`parameters`")
    private String parameters;

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
    @Column(name = "`send_time`")
    private Date sendTime;

    /**
     * 发送通道
     */
    @Column(name = "`send_channel`")
    private String sendChannel;

    /**
     * 请求ID
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
     * 获取此次消息请求码
     *
     * @return request_code - 此次消息请求码
     */
    @FieldDef(label="此次消息请求码", maxLength = 50)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getRequestCode() {
        return requestCode;
    }

    /**
     * 设置此次消息请求码
     *
     * @param requestCode 此次消息请求码
     */
    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * 获取来源市场
     *
     * @return market_code - 来源市场
     */
    @FieldDef(label="来源市场", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * 设置来源市场
     *
     * @param marketCode 来源市场
     */
    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    /**
     * 获取来源系统
     *
     * @return system_code - 来源系统
     */
    @FieldDef(label="来源系统", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * 设置来源系统
     *
     * @param systemCode 来源系统
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * 获取应用场景
     *
     * @return scene_code - 应用场景
     */
    @FieldDef(label="应用场景", maxLength = 50)
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
     * 获取电话号码
     *
     * @return cellphone - 电话号码
     */
    @FieldDef(label="电话号码", maxLength = 1000)
    @EditMode(editor = FieldEditor.Text)
    public String getCellphone() {
        return cellphone;
    }

    /**
     * 设置电话号码
     *
     * @param cellphone 电话号码
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * 获取接收时间
     *
     * @return receipt_time - 接收时间
     */
    @FieldDef(label="接收时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    public Date getReceiptTime() {
        return receiptTime;
    }

    /**
     * 设置接收时间
     *
     * @param receiptTime 接收时间
     */
    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    /**
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    @FieldDef(label="消息内容", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取发送状态
     *
     * @return send_state - 发送状态
     */
    @FieldDef(label="发送状态")
    @EditMode(editor = FieldEditor.Text, required = false)
    public Integer getSendState() {
        return sendState;
    }

    /**
     * 设置发送状态
     *
     * @param sendState 发送状态
     */
    public void setSendState(Integer sendState) {
        this.sendState = sendState;
    }

    /**
     * 获取发送时间
     *
     * @return send_time - 发送时间
     */
    @FieldDef(label="发送时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取发送通道
     *
     * @return send_channel - 发送通道
     */
    @FieldDef(label="发送通道", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getSendChannel() {
        return sendChannel;
    }

    /**
     * 设置发送通道
     *
     * @param sendChannel 发送通道
     */
    public void setSendChannel(String sendChannel) {
        this.sendChannel = sendChannel;
    }

    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getBizId() {
        return bizId;
    }
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    @FieldDef(label="备注", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}