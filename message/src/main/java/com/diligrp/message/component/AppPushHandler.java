package com.diligrp.message.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.uap.sdk.domain.User;
import com.dili.uap.sdk.domain.UserPushInfo;
import com.diligrp.message.common.enums.BizNumberTypeEnum;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.PushLog;
import com.diligrp.message.sdk.domain.input.AppPushInput;
import com.diligrp.message.service.PushLogService;
import com.diligrp.message.service.remote.UapUserRpcService;
import com.diligrp.message.service.remote.UidRpcService;
import com.diligrp.message.service.remote.impl.JPushServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 处理接收到的信息推送请求
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/9 20:39
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AppPushHandler {

    private final JPushServiceImpl jPushService;
    private final UidRpcService uidRpcService;
    private final PushLogService pushLogService;
    private final UapUserRpcService uapUserRpcService;

    /**
     * 处理接收到的信息
     * @param appPushInput 请求数据
     */
    public BaseOutput<Boolean> handler(AppPushInput appPushInput) {
        LocalDateTime receiptTime = LocalDateTime.now();
        //获取本次的推送请求码
        Optional<String> bizNumber = uidRpcService.getBizNumber(BizNumberTypeEnum.PUSH_REQUEST);
        String requestCode = bizNumber.orElse(String.valueOf(System.currentTimeMillis()));
        List<PushLog> pushLogList = Lists.newArrayList();
        String extras = null;
        if (CollectionUtil.isNotEmpty(appPushInput.getExtraMap())) {
            extras = JSONUtil.toJsonStr(appPushInput.getExtraMap());
        }
        PushLog pushLog = new PushLog();
        BeanUtil.copyProperties(appPushInput, pushLog);
        pushLog.setReceiptTime(receiptTime).setRequestCode(requestCode)
                .setPushChannel(MessageEnum.PushChannel.极光.getCode())
                .setExtras(extras)
                .setPushState(MessageEnum.SendStateEnum.WAITING.getCode());

        Map<String, Long> userPushIdMap = Maps.newHashMap();
        List<UserPushInfo> userPushInfoList = uapUserRpcService.listPushInfoByExample(appPushInput.getUserIds());
        //推送平台-注册ID 关联关系
        Map<String, List<String>> platformRegistrationIdMaps = Maps.newHashMap();
        if (CollectionUtil.isNotEmpty(userPushInfoList)) {
            platformRegistrationIdMaps = StreamEx.of(userPushInfoList).nonNull()
                    .mapToEntry(UserPushInfo::getPlatform, UserPushInfo::getPushId).nonNullKeys().nonNullValues()
                    .distinctValues().grouping();

            userPushInfoList.forEach(t->{
                PushLog temp = new PushLog();
                BeanUtil.copyProperties(pushLog, temp);
                temp.setRegistrationId(t.getPushId());
                temp.setUserId(userPushIdMap.get(t.getUserId()));
                temp.setPlatform(t.getPlatform());
                if (StrUtil.isBlank(t.getPlatform()) || StrUtil.isBlank(t.getPushId())) {
                    temp.setPushState(MessageEnum.SendStateEnum.FAILURE.getCode());
                    temp.setFailureMessage("推送平台或注册ID为空");
                }
                pushLogList.add(temp);
            });
        } else {
            pushLogList.add(pushLog);
        }
        pushLogService.batchInsert(pushLogList);
        return jPushService.pushHandler(appPushInput, requestCode,platformRegistrationIdMaps);
    }
}
