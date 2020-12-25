package com.diligrp.message.service.remote;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.redis.service.RedisUtil;
import com.dili.uap.sdk.domain.DataDictionaryValue;
import com.dili.uap.sdk.rpc.DataDictionaryRpc;
import com.diligrp.message.constants.MessageConstant;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@RequiredArgsConstructor
@Service
@Slf4j
public class DataDictionaryRpcService {

    private static String redisKeyPrefix = "message:dictionary:";

    private final DataDictionaryRpc dataDictionaryRpc;
    private final RedisUtil redisUtil;

    @Resource(name = "caffeineTimedCache")
    private Cache<String, String> caffeineTimedCache;

    /**
     * 查询需要发送邮件的用户列表
     * @return
     */
    public List<String> listToMail() {
        String messageErrorEmail = "message_error_email";
        String redisKey = redisKeyPrefix + messageErrorEmail;
        Long redisSize = redisUtil.getRedisTemplate().opsForList().size(redisKey);
        List<String> mails = Lists.newArrayList();
        //检查当前key在redis中是否存在
        if (null == redisSize || redisSize == 0) {
            BaseOutput<List<DataDictionaryValue>> out = dataDictionaryRpc.listDataDictionaryValueByDdCode(messageErrorEmail);
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

    /**
     * 根据条件查询数据字典信息
     * @param dataDictionaryValue
     * @return
     */
    public List<DataDictionaryValue> listDataDictionaryValue(DataDictionaryValue dataDictionaryValue) {
        try {
            BaseOutput<List<DataDictionaryValue>> listBaseOutput = dataDictionaryRpc.listDataDictionaryValue(dataDictionaryValue);
            if (Objects.nonNull(listBaseOutput) && listBaseOutput.isSuccess()) {
                return listBaseOutput.getData();
            }
        } catch (Exception e) {
            log.error(String.format("根据条件【%s】查询数据字典异常:%s", JSONUtil.toJsonStr(dataDictionaryValue), e.getMessage()), e);
        }
        return Collections.emptyList();
    }

    /**
     * 根据条件查询数据字典信息
     * @param ddCode 数据字典值
     * @param state  字典值状态 1-启用
     * @param marketId 数据字典值是否按市场隔离，如果为空，则不按市场隔离，否则按传入的具体市场id进行数据查询
     * @return
     */
    public List<DataDictionaryValue> listByDdCode(String ddCode, Integer state, Long marketId) {
        try {
            StringBuilder keyBuilder = new StringBuilder(MessageConstant.CACHE_KEY).append("_").append(ddCode);
            DataDictionaryValue dataDictionaryValue = DTOUtils.newInstance(DataDictionaryValue.class);
            dataDictionaryValue.setDdCode(ddCode);
            if (Objects.nonNull(state)) {
                dataDictionaryValue.setState(state);
                keyBuilder.append("_").append(state);
            }
            if (Objects.nonNull(marketId)) {
                dataDictionaryValue.setFirmId(marketId);
                keyBuilder.append("_").append(marketId);
            }
            String str = caffeineTimedCache.get(keyBuilder.toString(), t -> {
                BaseOutput<List<DataDictionaryValue>> listBaseOutput = dataDictionaryRpc.listDataDictionaryValue(dataDictionaryValue);
                if (listBaseOutput.isSuccess() && CollectionUtil.isNotEmpty(listBaseOutput.getData())) {
                    return JSONObject.toJSONString(listBaseOutput.getData());
                }
                return null;
            });
            if (StrUtil.isNotBlank(str)) {
                List<DataDictionaryValue> dto = JSONArray.parseArray(str, DataDictionaryValue.class);
                return dto;
            }
        } catch (Exception e) {
            log.error(String.format("根据ddCode【%s】及状态【%s】以及市场【%d】查询数据字典异常:%s", ddCode, state, marketId, e.getMessage()), e);
        }
        return Collections.emptyList();
    }
}
