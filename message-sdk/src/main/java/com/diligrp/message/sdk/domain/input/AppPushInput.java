package com.diligrp.message.sdk.domain.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.Set;

/**
 * APP 消息推送入参信息
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/9 15:01
 */
@Data
public class AppPushInput {

    /**
     * 所属市场
     */
    @NotBlank(message = "所属市场不能为空")
    private Long marketId;

    /**
     * 设备注册ID集合，支持多设备推送同一记录,如果为空，则推送所有用户
     */
    @Size(message = "单次推送最多1000个注册设备", max = 1000)
    private Set<String> registrationIds;

    /**
     * 设备注册ID集合，支持多设备推送同一记录,如果为空，则推送所有用户
     */
    @Size(message = "单次推送最多1000个注册客户", max = 1000)
    private Set<Long> userIds;

    /**
     * 推送平台
     * {@link com.diligrp.message.sdk.enums.PushPlatformEnum}
     * 可选值为：android,ios,winphone
     */
    @NotBlank(message = "推送平台不能为空")
    private String platform;

    /**
     * 通知内容
     */
    @NotBlank(message = "通知内容不能为空")
    private String alert;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 扩展内容
     */
    private Map<String,String> extraMap;
}
