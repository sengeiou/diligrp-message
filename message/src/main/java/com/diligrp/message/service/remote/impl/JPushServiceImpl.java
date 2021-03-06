package com.diligrp.message.service.remote.impl;

import cn.hutool.json.JSONUtil;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.config.JiGuangConfig;
import com.diligrp.message.domain.PushLog;
import com.diligrp.message.sdk.domain.input.AppPushInput;
import com.diligrp.message.sdk.enums.PushPlatformEnum;
import com.diligrp.message.service.PushLogService;
import com.diligrp.message.service.remote.IPushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.diligrp.message.sdk.enums.PushPlatformEnum.getInstance;


/**
 * 极光推送系统接入
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/8 17:23
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class JPushServiceImpl implements IPushService {


    private final JiGuangConfig jiGuangConfig;
    private final PushLogService pushLogService;

    @Override
    public BaseOutput<Boolean> pushHandler(AppPushInput appPushInput, String requestCode, String platformKey, List<String> registrationIds) {
        PushResult pushResult = null;
        String failureCode = null;
        String failureMessage = null;
        try {
            Optional<PushPayload> pushPayloadOptional = buildPushInfo(appPushInput, platformKey, registrationIds);
            if (pushPayloadOptional.isPresent()) {
                pushResult = jiGuangConfig.getJPushClient().sendPush(pushPayloadOptional.get());
                if (pushResult.isResultOK()) {
                    return BaseOutput.successData(Boolean.TRUE);
                }
                log.warn(String.format("请求码为[%s]的APP消息[%s]推送失败[%s]", requestCode, JSONUtil.toJsonStr(appPushInput), pushResult));
                return BaseOutput.failure("推送失败").setCode(String.valueOf(pushResult.error.getCode())).setData(Boolean.FALSE);
            } else {
                failureCode = ResultCode.INVALID_REQUEST;
                failureMessage = "推送平台不明确";
                return BaseOutput.failure("推送平台不明确").setCode(failureCode).setData(Boolean.FALSE);
            }
        } catch (Exception e) {
            failureCode = ResultCode.APP_ERROR;
            failureMessage = "系统异常";
            log.error(String.format("请求码为[%s]的APP消息[%s]推送异常[%s]", requestCode, JSONUtil.toJsonStr(appPushInput), e.getMessage()), e);
            return BaseOutput.failure("系统异常");
        } finally {
            LocalDateTime pushTime = LocalDateTime.now();
            //构造保存数据
            Integer pushState = MessageEnum.SendStateEnum.FAILURE.getCode();
            String messageId = null;
            String sendNo = null;
            if (Objects.nonNull(pushResult)) {
                messageId = String.valueOf(pushResult.msg_id);
                sendNo = String.valueOf(pushResult.sendno);
                if (pushResult.isResultOK()) {
                    pushState = MessageEnum.SendStateEnum.SUCCEED.getCode();
                } else {
                    failureCode = String.valueOf(pushResult.error.getCode());
                    failureMessage = String.valueOf(pushResult.error.getMessage());
                }
            }
            PushLog domain = new PushLog().setFailureMessage(failureMessage).setFailureCode(failureCode)
                    .setPushState(pushState).setPushTime(pushTime)
                    .setPushChannel(MessageEnum.PushChannel.极光.getCode())
                    .setMessageId(messageId).setSendNo(sendNo);
            PushLog condition = new PushLog().setRequestCode(requestCode).setPushState(MessageEnum.SendStateEnum.WAITING.getCode());
            pushLogService.updateSelectiveByExample(domain, condition);
        }
    }

    /**
     * 构建通知消息
     * @return
     */
    private Optional<PushPayload> buildPushInfo(AppPushInput appPushInput, String platformKey, List<String> registrationIds) {
        PushPlatformEnum platform = getInstance(platformKey);
        if (Objects.isNull(platform)) {
            return Optional.empty();
        }
        PushPayload.Builder builder = PushPayload.newBuilder();
        builder.setAudience(Audience.registrationId(registrationIds));
        switch (platform) {
            case Android:
                builder.setPlatform(Platform.android())
                        .setNotification(Notification.android(appPushInput.getAlert(), appPushInput.getTitle(), appPushInput.getExtraMap()));
                break;
            case IOS:
                builder.setPlatform(Platform.ios())
                        .setNotification(Notification.ios(appPushInput.getAlert(), appPushInput.getExtraMap()))
                        .setOptions(Options.newBuilder()
                                .setApnsProduction(jiGuangConfig.getApnsProduction())
                                .build());
                break;
            case WinPhone:
                builder.setPlatform(Platform.winphone())
                        .setNotification(Notification.winphone(appPushInput.getAlert(), appPushInput.getExtraMap()));
                break;
            default:
        }
        return Optional.of(builder.build());
    }
}
