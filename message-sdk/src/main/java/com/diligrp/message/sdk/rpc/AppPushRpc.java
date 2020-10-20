package com.diligrp.message.sdk.rpc;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.sdk.domain.input.AppPushInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * APP 消息推送RPC
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/12 9:54
 */
@FeignClient(name = "message-service", contextId = "appPushRpc")
public interface AppPushRpc {

    /**
     * 根据条件匹配规则并返回费用信息
     * @param appPushInput 消息推送数据
     * @return 推送结果
     */
    @PostMapping(value = "/api/appPush/receiveMessage")
    BaseOutput<Boolean> receiveMessage(AppPushInput appPushInput);
}
