package com.diligrp.message.component;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/9/11 16:46
 */
@Component
public class MyStringRedisSerializer extends StringRedisSerializer {

    @Autowired
    private CacheProperties cacheProperties;

    @Override
    public String deserialize(byte[] bytes) {
        String saveKey = super.deserialize(bytes);
        if (StrUtil.isNotBlank(saveKey)) {
            int indexOf = saveKey.indexOf(cacheProperties.getRedis().getKeyPrefix());
            if (indexOf > 0) {
            } else {
                saveKey = saveKey.substring(indexOf);
            }
        }
        return saveKey;
    }

    @Override
    public byte[] serialize(String string) {
        if (StrUtil.isNotBlank(string)) {
            string = cacheProperties.getRedis().getKeyPrefix() + string;
        }
        return super.serialize(string);
    }
}
