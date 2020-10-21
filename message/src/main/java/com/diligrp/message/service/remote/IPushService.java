package com.diligrp.message.service.remote;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.sdk.domain.input.AppPushInput;

import java.util.List;

/**
 * APP 消息推送
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/8 17:21
 */
public interface IPushService {

    /**
     * app 平台内容推送
     * @param appPushInput 推送内容参数
     * @param requestCode 此次推送请求码
     * @param platformKey 推送平台
     * @param registrationIds 推送平台-注册ID集合
     * @return
     */
    BaseOutput<Boolean> pushHandler(AppPushInput appPushInput, String requestCode, String platformKey, List<String> registrationIds);
}
