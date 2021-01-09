package com.diligrp.message.common.enums;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

public class MessageEnum {

    public enum ChannelEnum {

        ALI_YUN("alidayu", "阿里云"),
        CHINA_MOBILE("china_mobile", "中国移动云MAS"),
        WEBCHINESE_SMS("webchinese_sms", "网建短信通"),
        ;

        @Getter
        private String name;
        @Getter
        private String code;

        ChannelEnum(String code, String name){
            this.code = code;
            this.name = name;
        }

        private static Map<String, ChannelEnum> initMaps = Maps.newHashMap();

        static {
            for (ChannelEnum anEnum : ChannelEnum.values()) {
                initMaps.put(anEnum.code, anEnum);
            }
        }

        /**
         * 获取一个枚举实例
         * @param code
         * @return
         */
        public static ChannelEnum getInstance(String code) {
            return initMaps.getOrDefault(code,null);
        }
    }

    /**
     * 消息发送状态
     */
    public enum SendStateEnum {
        WAITING(0,"等待中"),
        SUCCEED(1, "成功"),
        FAILURE(2, "失败"),
        ;
        @Getter
        private String name;
        @Getter
        private Integer code;

        SendStateEnum(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        /**
         * 获取某个枚举值实例信息
         *
         * @param code
         * @return
         */
        public static SendStateEnum getInstance(Integer code) {
            for (MessageEnum.SendStateEnum mce : MessageEnum.SendStateEnum.values()) {
                if (mce.getCode().equals(code)) {
                    return mce;
                }
            }
            return null;
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

    /**
     * 白名单数据来源
     */
    public enum WhitelistSourceEnum {

        SYSTEM(10, "系统加入"),
        MANUAL(20, "手动添加"),
        ;

        @Getter
        private String name;
        @Getter
        private Integer code;

        WhitelistSourceEnum(Integer code, String name){
            this.code = code;
            this.name = name;
        }

        /**
         * 获取某个枚举值实例信息
         *
         * @param code
         * @return
         */
        public static WhitelistSourceEnum getInstance(Integer code) {
            for (MessageEnum.WhitelistSourceEnum mce : MessageEnum.WhitelistSourceEnum.values()) {
                if (mce.getCode().equals(code)) {
                    return mce;
                }
            }
            return null;
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

    /**
     * boolean类型的枚举定义
     */
    public enum BooleanEnum {

        FALSE(false, "否"),
        TRUE(true, "是"),
        ;

        @Getter
        private String name;
        @Getter
        private Boolean code;

        BooleanEnum(Boolean code, String name){
            this.code = code;
            this.name = name;
        }

        public static BooleanEnum getInstance(Boolean code) {
            for (BooleanEnum be : BooleanEnum.values()) {
                if (be.getCode().equals(code)) {
                    return be;
                }
            }
            return null;
        }
    }

    /**
     * 白名单状态类型的枚举定义
     */
    public enum WhitelistStatus {

        USELESS(10, "待生效"),
        ACTIVE(20, "生效中"),
        EXPIRED(30, "已过期"),
        ;

        @Getter
        private String name;
        @Getter
        private Integer code;

        WhitelistStatus(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static WhitelistStatus getInstance(Integer code) {
            for (WhitelistStatus ws : WhitelistStatus.values()) {
                if (ws.getCode().equals(code)) {
                    return ws;
                }
            }
            return null;
        }
    }


    /**
     * APP 消息推送通道
     */
    public enum PushChannel {

        极光(1, "极光"),
        ;

        @Getter
        private String name;
        @Getter
        private Integer code;

        PushChannel(Integer code, String name){
            this.code = code;
            this.name = name;
        }
        public static PushChannel getInstance(Integer code) {
            for (MessageEnum.PushChannel pc : MessageEnum.PushChannel.values()) {
                if (pc.getCode().equals(code)) {
                    return pc;
                }
            }
            return null;
        }
    }
}
