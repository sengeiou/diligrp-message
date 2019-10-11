package com.diligrp.message.service.remote;

import cn.hutool.core.collection.CollectionUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.redis.service.RedisUtil;
import com.dili.uap.sdk.domain.DataDictionaryValue;
import com.diligrp.message.rpc.DataDictionaryRpc;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/9/11 10:54
 */
@Service
public class DataDictionaryRpcService {

    private static String redisKeyPrefix = "message:dictionary:";

    @Autowired
    private DataDictionaryRpc dataDictionaryRpc;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询需要发送邮件的用户列表
     * @return
     */
    public List<String> listToMail() {
        String messageErrorEmail = "message_error_email";
        String redisKey = redisKeyPrefix + messageErrorEmail;
        Long redisSize = redisUtil.getRedisTemplate().opsForList().size(redisKey);
        List<String> mails = Lists.newArrayList();
        if (null == redisSize || redisSize == 0) {
            DataDictionaryValue example = DTOUtils.newDTO(DataDictionaryValue.class);
            example.setDdCode(messageErrorEmail);
            BaseOutput<List<DataDictionaryValue>> out = dataDictionaryRpc.list(example);
            if (out == null || !out.isSuccess()) {
                return Collections.emptyList();
            }
            mails = out.getData().stream().filter(Objects::nonNull).map(DataDictionaryValue::getCode).filter(Objects::nonNull).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(mails)) {
                redisUtil.getRedisTemplate().opsForList().leftPushAll(redisKey, mails);
                redisUtil.expire(redisKey, 7, TimeUnit.DAYS);
            }
        } else {
            mails = redisUtil.getRedisTemplate().opsForList().range(redisKey, 0, redisSize);
        }
        return mails;
    }
}
