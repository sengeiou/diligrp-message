package com.diligrp.message.domain.vo;

import com.diligrp.message.domain.MarketChannel;

import javax.persistence.Column;

public class MarketChannelVo extends MarketChannel {
    /**
     * 联合分组后的，通道编码，默认以,隔开
     */
    @Column(name = "`group_channel`")
    private String groupChannel;

    /**
     * 联合分组后的，通道签名，默认以,隔开
     */
    @Column(name = "`group_signature`")
    private String group_signature;

    public String getGroupChannel() {
        return groupChannel;
    }

    public void setGroupChannel(String groupChannel) {
        this.groupChannel = groupChannel;
    }

    public String getGroup_signature() {
        return group_signature;
    }

    public void setGroup_signature(String group_signature) {
        this.group_signature = group_signature;
    }
}
