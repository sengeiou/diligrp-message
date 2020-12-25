package com.diligrp.message.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/12/25 10:14
 */
@Configuration
public class CaffeineConfig {

    /**
     * 按时间存储本地缓存,默认存10分钟
     */
    @Bean("caffeineTimedCache")
    public Cache<String, String> caffeineTimedCache() {
        return Caffeine.newBuilder().expireAfterWrite(10L, TimeUnit.MINUTES).build();
    }

    /**
     * 按数量存储本地缓存,默认存100条
     * 超过指定大小时，自动清除最早加入的
     */
    @Bean("caffeineMaxSizeCache")
    public Cache<String, String> caffeineMaxSizeCache() {
        return Caffeine.newBuilder().maximumSize(100L).build();
    }
}
