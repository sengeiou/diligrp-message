package com.diligrp.message.domain.vo;

import com.diligrp.message.domain.Triggers;

import javax.persistence.Column;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/3/31 18:09
 */
public class TriggersVo extends Triggers {

    /**
     * 联合分组后的，通道代码，默认以,隔开
     */
    @Column(name = "`group_channel`")
    private String groupChannel;

    /**
     * 联合分组后的，通道代码，默认以,隔开
     */
    @Column(name = "`group_template_code`")
    private String groupTemplateCode;

    public String getGroupChannel() {
        return groupChannel;
    }

    public void setGroupChannel(String groupChannel) {
        this.groupChannel = groupChannel;
    }

    public String getGroupTemplateCode() {
        return groupTemplateCode;
    }

    public void setGroupTemplateCode(String groupTemplateCode) {
        this.groupTemplateCode = groupTemplateCode;
    }
}
