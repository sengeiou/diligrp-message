package com.diligrp.message.common.enums;

import lombok.Getter;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 16:46
 */
public enum BizNumberTypeEnum {

    TRIGGERS("message_trigger", "触发点编码"),
    SEND_REQUEST("message_send_request", "消息发送请求码"),
    PUSH_REQUEST("message_push_request", "消息推送请求码"),
    ;

    @Getter
    private String type;
    @Getter
    private String name;

    /**
     * @param type  编码业务类型
     * @param name  名称
     */
    BizNumberTypeEnum(String type, String name){
        this.type = type;
        this.name = name;
    }

    public static BizNumberTypeEnum getInstance(String type) {
        for (BizNumberTypeEnum bizNumberType : BizNumberTypeEnum.values()) {
            if (bizNumberType.getType().equals(type)) {
                return bizNumberType;
            }
        }
        return null;
    }
}
