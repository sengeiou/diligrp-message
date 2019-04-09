package com.diligrp.message.service.remote;

import com.diligrp.message.domain.TriggersTemplate;

import java.util.List;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 17:13
 */
public interface SmsService {

    /**
     * 消息发送
     * @param sendLogId
     * @param templateList
     */
    void sendSms(Long sendLogId, List<TriggersTemplate> templateList);
}
