package com.diligrp.message.domain.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * <B>接收业务系统的信息发送请求字段</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 11:09
 */
@Getter
@Setter
public class MessageInfoVo {

    /**
     * 所属市场
     */
    @NotBlank(message = "所属市场不能为空")
    private String marketCode;

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
     * 手机号
     */
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
}
