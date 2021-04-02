package com.diligrp.message.sdk.constants;

/**
 * 消息中心MQ相关配置
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/12/28 10:44
 */
public interface MqConstant {

    /**
     * MQ 交换机配置
     */
    String MQ_MESSAGE_TOPIC_EXCHANGE = "dili.message.topicExchange";
    /**
     * MQ 短信发送Queue
     */
    String MQ_MESSAGE_SMS_QUEUE = "dili.message.smsQueue";
    /**
     * MQ 短信发送Key
     */
    String MQ_MESSAGE_SMS_KEY = "dili.message.smsMessageKey";
}
