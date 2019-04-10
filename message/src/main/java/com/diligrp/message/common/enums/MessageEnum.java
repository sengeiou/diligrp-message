package com.diligrp.message.common.enums;

public class MessageEnum {

    public enum ChannelEnum {

        ALIDAYU("alidayu", "阿里大于"),
        CHINA_MOBILE("china_mobile", "中国移动云MAS"),
        WEBCHINESE_SMS("webchinese_sms", "网建短信通"),
        ;

        private String name;
        private String code;

        ChannelEnum(String code, String name){
            this.code = code;
            this.name = name;
        }

        public static ChannelEnum getChannelEnum(String code) {
            for (MessageEnum.ChannelEnum mc : MessageEnum.ChannelEnum.values()) {
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
        WAITING(0,"等待中"),
        SUCCEED(1, "成功"),
        FAILURE(2, "失败"),
        ;
        private String name;
        private Integer code;

        SendStateEnum(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        public static SendStateEnum getSendStateEnum(Integer code) {
            for (MessageEnum.SendStateEnum mce : MessageEnum.SendStateEnum.values()) {
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

    public enum MessageSourceEnum {

        SYSTEM(10, "系统加入"),
        MANUAL(20, "手动添加"),
        ;

        private String name;
        private Integer code;

        MessageSourceEnum(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        public static MessageSourceEnum getMessageSourceEnum(Integer code) {
            for (MessageEnum.MessageSourceEnum mce : MessageEnum.MessageSourceEnum.values()) {
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

    public enum DeletedEnum {

        NO(0, "否"),
        YES(1, "是"),
        ;

        private String name;
        private Integer code;

        DeletedEnum(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        public static DeletedEnum getDeletedEnum(Integer code) {
            for (MessageEnum.DeletedEnum mce : MessageEnum.DeletedEnum.values()) {
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
