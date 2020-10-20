package com.diligrp.message.config;

import cn.jpush.api.JPushClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 极光推送配置类
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/11 14:12
 */
@Configuration
@ConfigurationProperties(prefix = "dili.jpush")
public class JiGuangConfig {

    /**
     * jpush key
     */
    @Getter
    @Setter
    private String appKey;

    /**
     * jpush secret
     */
    @Getter
    @Setter
    private String secret;

    /**
     * jpush 环境 是否为生产环境 true - 是
     */
    @Getter
    @Setter
    private Boolean apnsProduction;

    /**
     * 推送客户端
     */
    private JPushClient jPushClient;

    // 构造推送客户端
    @PostConstruct
    public void initJPushClient() {
        jPushClient = new JPushClient(secret, appKey);
    }

    /**
     * 获取推送客户端
     * @return 推送客户端实例
     */
    public JPushClient getJPushClient() {
        return jPushClient;
    }
}
