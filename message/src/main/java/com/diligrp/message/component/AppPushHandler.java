package com.diligrp.message.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.uap.sdk.domain.User;
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
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
        if (CollectionUtil.isNotEmpty(appPushInput.getUserIds())) {
            List<User> userList = uapUserRpcService.listUserByIds(appPushInput.getUserIds());
            if (CollectionUtil.isNotEmpty(userList)) {
                userPushIdMap = userList.stream().collect(Collectors.toMap(User::getPushId, User::getId));
                if (Objects.isNull(appPushInput.getRegistrationIds())) {
                    appPushInput.setRegistrationIds(userPushIdMap.keySet());
                } else {
                    appPushInput.getRegistrationIds().addAll(userPushIdMap.keySet());
                }
            }
        }
        if (CollectionUtil.isNotEmpty(appPushInput.getRegistrationIds())) {
            for (String registrationId : appPushInput.getRegistrationIds()) {
                PushLog temp = new PushLog();
                BeanUtil.copyProperties(pushLog, temp);
                temp.setRegistrationId(registrationId);
                temp.setUserId(userPushIdMap.get(registrationId));
                pushLogList.add(temp);
            }
        } else {
            pushLogList.add(pushLog);
        }
        pushLogService.batchInsert(pushLogList);
        return jPushService.pushHandler(appPushInput, requestCode);
    }
}
