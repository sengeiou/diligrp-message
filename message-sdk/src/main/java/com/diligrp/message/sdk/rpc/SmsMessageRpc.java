package com.diligrp.message.sdk.rpc;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.sdk.domain.input.MessageInfoInput;
import com.diligrp.message.sdk.domain.input.WhitelistCustomerInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 短信推送相关RPC
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/12 13:57
 */
@FeignClient(name = "message-service", contextId = "smsMessageRpc")
public interface SmsMessageRpc {

    /**
     * 接收业务系统的短信发送
     * @param messageInfoInput 短信内容信息
     * @return
     */
    @PostMapping(value = "/messageApi/receiveMessage.api")
    BaseOutput receiveMessage(MessageInfoInput messageInfoInput);

    /**
     * 接收业务系统的用户信息，并存入白名单信息
     * @param input 用户信息
     * @return
     */
    @PostMapping(value = "/messageApi/whitelistCustomer.api")
    BaseOutput whitelistCustomer(WhitelistCustomerInput input);

    /**
     * 删除白名单用户
     * @param marketCode 用户所属市场
     * @param id         用户ID
     * @return
     */
    @PostMapping(value = "/messageApi/delWhitelistCustomer")
    BaseOutput delWhitelistCustomer(@RequestParam("marketCode") String marketCode, @RequestParam("id") Long id);

}
