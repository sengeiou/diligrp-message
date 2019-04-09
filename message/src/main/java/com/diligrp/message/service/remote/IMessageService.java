package com.diligrp.message.service.remote;

import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;

/**
 * <B>消息发送类统一接口</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/9 14:31
 */
public interface IMessageService {

    /**
     * 消息发送实现
     * @param object 参数信息
     * @return
     */
    BaseOutput<String> sendSMS(JSONObject object);

}
