package com.diligrp.message.sdk.enums;

import lombok.Getter;

/**
 * APP 推送平台终端类型
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/9 15:10
 */
public enum PushPlatformEnum {

    Android("android"),
    IOS("ios"),
    WinPhone("winphone"),
    ;

    PushPlatformEnum(String value) {
        this.value = value;
    }

    @Getter
    private  String value;

    /**
     * 获取某个枚举值实例信息
     * @param value
     * @return
     */
    public static PushPlatformEnum getInstance(String value){
        for (PushPlatformEnum platform : PushPlatformEnum.values()) {
            if (platform.getValue().equals(value)){
                return platform;
            }
        }
        return null;
    }
}
