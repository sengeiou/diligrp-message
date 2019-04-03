package com.diligrp.message.domain.vo;

import com.dili.ss.domain.annotation.Operator;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.TriggersTemplate;

import javax.persistence.Column;
import java.util.List;

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

    /**
     * 市场编码
     */
    @Column(name = "`market_code`")
    private List<String> marketCodeList;

    /**
     * 触发点对应的模板信息
     */
    private List<TriggersTemplate> templateList;

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
    @Operator(Operator.IN)
    public List<String> getMarketCodeList() {
        return marketCodeList;
    }
    public void setMarketCodeList(List<String> marketCodeList) {
        this.marketCodeList = marketCodeList;
    }
    public List<TriggersTemplate> getTemplateList() {
        return templateList;
    }
    public void setTemplateList(List<TriggersTemplate> templateList) {
        this.templateList = templateList;
    }
}
