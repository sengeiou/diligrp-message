package com.diligrp.message.common.enums;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 17:32
 */
public class MessageSendEnum {

    /**
     * 消息发送状态
     */
    public enum SendState {

        WAITING(0,"等待中"),
        SUCCEED(1, "成功"),
        FAILURE(2, "失败"),
        ;

        private String name;
        private Integer code;

        SendState(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        public static SendState getSendState(Integer code) {
            for (SendState anEnum : SendState.values()) {
                if (anEnum.getCode().equals(code)) {
                    return anEnum;
                }
            }
            return null;
        }

        public Integer getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
    }
}
