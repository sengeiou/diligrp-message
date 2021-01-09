package com.diligrp.message.component;

import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.sdk.constants.MqConstant;
import com.diligrp.message.sdk.domain.input.MessageInfoInput;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 短信消息MQ监听
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/12/28 10:48
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class SmsListener {

    private final MessageInfoHandler messageInfoHandler;

    /**
     * 短信消息监听器
     * @param message MQ消息
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.MQ_MESSAGE_SMS_QUEUE, autoDelete = "false"),
            exchange = @Exchange(value = MqConstant.MQ_MESSAGE_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = MqConstant.MQ_MESSAGE_SMS_KEY
    ), concurrency = "5")
    public void processSms(Channel channel, Message message) {
        try {
            String data = new String(message.getBody(), "UTF-8");
            log.info("获取到的body数据:" + data);
            MessageInfoInput input = JSONObject.parseObject(data, MessageInfoInput.class);
            BaseOutput handler = messageInfoHandler.handler(input);
            if (!handler.isSuccess()) {
                log.warn(String.format("消息对象: [%s] 发送失败 {%s},请求码:%s", data, handler.getMessage(), handler.getMetadata()));
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            //有可能再basicAck时抛出异常
            log.warn(String.format("消息对象: [%s] basicAck出错 {%s}", message, e.getMessage()), e);
        } catch (Exception e) {
            log.error(String.format("转换对象: {%s} 出错:%s", message, e.getMessage()), e);
            // redelivered = true, 表明该消息是重复处理消息
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            try {
                if (redelivered) {
                    /**
                     * 1. 对于重复处理的队列消息做补偿机制处理
                     * 2. 从队列中移除该消息，防止队列阻塞
                     */
                    // 消息已重复处理失败, 扔掉消息
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                    log.error(String.format("消息 %s 重新处理失败，扔掉消息", message));
                } else {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            } catch (IOException ex) {
                log.error(String.format("消息 %s 重放回队列失败:%s", message, ex.getMessage()), ex);
            }
        }
    }
}
