package com.diligrp.message.sdk.domain.input;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/12 10:48
 */
@Getter
@Setter
public class MessageInfoInput {

    /**
     * 短信通道市场
     */
    @NotBlank(message = "短信通道市场不能为空")
    private String marketCode;

    /**
     * 业务产生市场
     */
    @NotBlank(message = "业务产生市场不能为空")
    private String businessMarketCode;

    /**
     * 系统编码
     */
    @NotBlank(message = "系统编码不能为空")
    private String systemCode;

    /**
     * 应用场景
     */
    @NotBlank(message = "应用场景不能为空")
    private String sceneCode;

    /**
     * 手机号,多个以英文逗号隔开
     */
    @NotBlank(message = "发送手机号不能为空")
    private String cellphone;

    /**
     * json格式的参数
     */
    private String parameters;

    /**
     * 指定发送模板编码
     */
    private String templateCode;

    /**
     * 请求IP地址
     */
    private String ip;

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
        if (StringUtils.hasText(businessMarketCode)) {
            this.setBusinessMarketCode(marketCode);
        }
    }
}
