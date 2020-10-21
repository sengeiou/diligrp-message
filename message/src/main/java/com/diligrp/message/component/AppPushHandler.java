package com.diligrp.message.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        String extras = null;
        if (CollectionUtil.isNotEmpty(appPushInput.getExtraMap())) {
            extras = JSONUtil.toJsonStr(appPushInput.getExtraMap());
        }
        PushLog pushLog = new PushLog();
        BeanUtil.copyProperties(appPushInput, pushLog);
        pushLog.setReceiptTime(receiptTime).setPushChannel(MessageEnum.PushChannel.极光.getCode())
                .setExtras(extras).setPushState(MessageEnum.SendStateEnum.WAITING.getCode());
        List<UserPushInfo> userPushInfoList = uapUserRpcService.listPushInfoByExample(appPushInput.getUserIds());
        //推送平台-注册ID 关联关系
        Map<String, List<UserPushInfo>> platformRegistrationIdMaps = Maps.newHashMap();
        if (CollectionUtil.isNotEmpty(userPushInfoList)) {
            platformRegistrationIdMaps = StreamEx.of(userPushInfoList).nonNull()
                    .mapToEntry(UserPushInfo::getPlatform, Function.identity()).nonNullKeys().nonNullValues()
                    .distinctValues().grouping();
            Boolean result = Boolean.TRUE;
            String code = null;
            String message = null;
            for (Map.Entry<String, List<UserPushInfo>> entry : platformRegistrationIdMaps.entrySet()) {
                System.out.println(entry.getKey() + "：" + entry.getValue());
                //获取本次的推送请求码
                Optional<String> bizNumber = uidRpcService.getBizNumber(BizNumberTypeEnum.PUSH_REQUEST, true);
                List<String> pushIdList = entry.getValue().stream().map(UserPushInfo::getPushId).collect(Collectors.toList());
                //生成保存数据
                generateSaveData(pushLog, bizNumber.get(), entry.getValue());
                BaseOutput<Boolean> output = jPushService.pushHandler(appPushInput, bizNumber.get(), entry.getKey(), pushIdList);
                if (!output.getData()) {
                    code = output.getCode();
                    message = output.getMessage();
                    result = output.getData();
                }
            }
            if (result) {
                return BaseOutput.successData(result);
            }
            return BaseOutput.failure(message).setCode(code).setData(false);
        } else {
            Optional<String> bizNumber = uidRpcService.getBizNumber(BizNumberTypeEnum.PUSH_REQUEST, true);
            List<PushLog> pushLogList = Lists.newArrayList();
            pushLog.setRequestCode(bizNumber.get());
            userPushInfoList.forEach(t -> {
                PushLog temp = new PushLog();
                BeanUtil.copyProperties(pushLog, temp);
                temp.setUserId(t.getUserId()).setPushState(MessageEnum.SendStateEnum.FAILURE.getCode()).setFailureMessage("未找到对应的推送对象");
                pushLogList.add(temp);
            });
            pushLogService.batchInsert(pushLogList);
            log.warn(String.format("根据参数[%s]未从用户推送信息中找到对应的对象", JSONUtil.toJsonStr(appPushInput)));
            return BaseOutput.failure("未找到推送对象").setCode(ResultCode.UNAUTHORIZED);
        }
    }

    /**
     * 生成保存数据
     * @param pushLog
     * @param requestCode 请求码
     * @param userPushInfoList
     */
    private void generateSaveData(PushLog pushLog,String requestCode,List<UserPushInfo> userPushInfoList){
        List<PushLog> pushLogList = Lists.newArrayList();
        userPushInfoList.forEach(t -> {
            PushLog temp = new PushLog();
            BeanUtil.copyProperties(pushLog, temp);
            temp.setRegistrationId(t.getPushId()).setUserId(t.getUserId()).setPlatform(t.getPlatform()).setRequestCode(requestCode);
            if (StrUtil.isBlank(t.getPlatform()) || StrUtil.isBlank(t.getPushId())) {
                temp.setPushState(MessageEnum.SendStateEnum.FAILURE.getCode());
                temp.setFailureMessage("推送平台或注册ID为空");
            }
            pushLogList.add(temp);
        });
        pushLogService.batchInsert(pushLogList);
    }
}
