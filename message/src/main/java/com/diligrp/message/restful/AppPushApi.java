package com.diligrp.message.restful;

import cn.hutool.json.JSONUtil;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.component.AppPushHandler;
import com.diligrp.message.sdk.domain.input.AppPushInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP 消息推送
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/9 20:27
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appPush")
public class AppPushApi {

    private final AppPushHandler pushHandler;

    /**
     * 接收业务系统的app消息推送数据
     * @param appPushInput 消息推送数据
     * @return 推送结果
     */
    @PostMapping(value = "/receiveMessage")
    public BaseOutput<Boolean> receiveMessage(@RequestBody @Validated AppPushInput appPushInput, BindingResult br) {
        if (br.hasErrors()) {
            log.warn(String.format("APP消息[%s]推送失败[%s]", JSONUtil.toJsonStr(appPushInput), br.getFieldError().getDefaultMessage()));
            return BaseOutput.failure(br.getFieldError().getDefaultMessage()).setCode(ResultCode.INVALID_REQUEST).setData(Boolean.FALSE);
        }
        try {
            return pushHandler.handler(appPushInput);
        } catch (Exception e) {
            log.error(String.format("APP消息[%s]推送异常[%s]", JSONUtil.toJsonStr(appPushInput), e.getMessage()), e);
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR).setData(Boolean.FALSE);
        }
    }
}
