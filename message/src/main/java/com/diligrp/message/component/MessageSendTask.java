package com.diligrp.message.component;

import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.service.remote.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * <B>消息发送的任务注册</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 17:07
 */
@Component
public class MessageSendTask {

    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private SmsService smsService;

    /**
     * 规则信息注册到任务中
     * @param id
     * @param date
     */
    public void registerSMS(final Long id, final Date date, final List<TriggersTemplate> templateList) {
        Runnable task = () -> {
            smsService.sendSms(id,templateList);
        };
        taskScheduler.schedule(task, date);
    }
}
