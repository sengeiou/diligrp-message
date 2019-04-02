package com.diligrp.message.common.enums;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/2 15:34
 */
public class TriggersEnum {

    public enum EnabledStateEnum {

        ENABLED(1, "已启用"),
        DISABLED(0, "已禁用"),
        ;

        private String name;
        private Integer code;

        EnabledStateEnum(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        public static EnabledStateEnum getEnabledState(Integer code) {
            for (EnabledStateEnum anEnum : EnabledStateEnum.values()) {
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
