package com.diligrp.message.sdk.domain.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "所属市场不能为空")
    private Long marketId;

    /**
     * 设备注册ID集合，支持多设备推送同一记录,如果为空，则推送所有用户
     */
    @NotNull(message = "推送人群不能为空")
    @Size(message = "单次推送客户数量在1-1000之间", max = 1000, min = 1)
    private Set<Long> userIds;

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
