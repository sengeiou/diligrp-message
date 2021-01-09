package com.diligrp.message.common.enums;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

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

        @Getter
        private String name;
        @Getter
        private Integer code;

        EnabledStateEnum(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        private static Map<Integer, EnabledStateEnum> initMaps = Maps.newHashMap();

        static {
            for (EnabledStateEnum anEnum : EnabledStateEnum.values()) {
                initMaps.put(anEnum.code, anEnum);
            }
        }

        /**
         * 获取某个枚举值实例信息
         *
         * @param code
         * @return
         */
        public static EnabledStateEnum getInstance(Integer code) {
            return initMaps.getOrDefault(code, null);
        }

        /**
         * 对比枚举值是否相等
         * @param code
         * @return
         */
        public Boolean equalsToCode(Integer code) {
            return this.getCode().equals(code);
        }
    }
}
