package com.diligrp.message.domain;

import com.dili.ss.domain.BaseDomain;

import javax.persistence.*;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 16:38
 */
@Table(name = "message_sequence_number")
public class SequenceNumber extends BaseDomain {

    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日期格式
     */
    @Column(name = "`date`")
    private String date;

    /**
     * 编码分类
     */
    @Column(name = "`type`")
    private String type;

    /**
     * 编码当前最大值
     */
    @Column(name = "`number`")
    private Integer number;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
}
