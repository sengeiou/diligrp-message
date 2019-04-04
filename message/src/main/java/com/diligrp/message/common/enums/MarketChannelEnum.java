package com.diligrp.message.common.enums;

public class MarketChannelEnum {

    public enum ChannelEnum {

        ALIDAYU("alidayu", "阿里大于"),
        MOBILE("mobile", "移动"),
        ;

        private String name;
        private String code;

        ChannelEnum(String code, String name){
            this.code = code;
            this.name = name;
        }

        public static ChannelEnum getChannelEnum(String code) {
            for (MarketChannelEnum.ChannelEnum mc : MarketChannelEnum.ChannelEnum.values()) {
                if (mc.getCode().equals(code)) {
                    return mc;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum SendStateEnum {

        FAILURE(-1, "失败"),
        SUCCESS(1, "成功"),
        ;

        private String name;
        private Integer code;

        SendStateEnum(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        public static SendStateEnum getSendStateEnum(Integer code) {
            for (MarketChannelEnum.SendStateEnum mce : MarketChannelEnum.SendStateEnum.values()) {
                if (mce.getCode().equals(code)) {
                    return mce;
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
