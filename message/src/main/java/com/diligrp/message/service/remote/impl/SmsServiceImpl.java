package com.diligrp.message.service.remote.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.service.MarketChannelService;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.remote.SmsService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 17:47
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SendLogService sendLogService;
    @Autowired
    private MarketChannelService marketChannelService;

    @Resource
    private AlidayuSmsImpl alidayuSmsImpl;

    /**
     * 消息发送
     *
     * @param sendLogId
     * @param templateList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendSms(Long sendLogId, List<TriggersTemplate> templateList) {
        SendLog sendLog = sendLogService.get(sendLogId);
        Map<Long, MarketChannel> marketChannelMap = marketChannelService.queryByMarket(sendLog.getMarketCode());
        if (CollectionUtil.isEmpty(marketChannelMap)) {
            sendLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
            sendLog.setRemarks("市场通道未配置");
            sendLogService.update(sendLog);
            return;
        }
        Boolean flag = false;
        List<SendLog> sendLogs = Lists.newArrayList();
        for (TriggersTemplate t : templateList) {
            String[] chanelId = t.getMarketChannelIds().split("#");
            for (String s : chanelId) {
                //获取对应的账号
                MarketChannel marketChannel = marketChannelMap.get(Long.valueOf(s));
                if (null != marketChannel) {
                    JSONObject object = new JSONObject();
                    object.put("accessKey", marketChannel.getAccessKey());
                    object.put("secret", marketChannel.getSecret());
                    object.put("sign", marketChannel.getSignature());
                    object.put("phones", sendLog.getCellphone());
                    object.put("templateCode", t.getTemplateCode());
                    object.put("parameters", sendLog.getParameters());
                    BaseOutput<String> output = null;
                    if (t.getChannel().equals(MessageEnum.ChannelEnum.ALIDAYU.getCode())) {
                        output = alidayuSmsImpl.sendSMS(object);
                    }
                    if (null != output) {
                        sendLog.setSendTime(new Date());
                        sendLog.setRequestId(output.getData());
                        sendLog.setContent(produceContent(t.getTemplateContent(), JSONObject.parseObject(sendLog.getParameters())));
                        if (output.isSuccess()) {
                            sendLog.setBizId(output.getResult());
                            sendLog.setSendState(MessageEnum.SendStateEnum.SUCCEED.getCode());
                            flag = true;
                            break;
                        } else {
                            SendLog log = new SendLog();
                            BeanUtil.copyProperties(sendLog, log);
                            log.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
                            log.setRemarks(output.getResult());
                            log.setId(null);
                            sendLogs.add(log);
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        //如果最后，都没没有发送成功
        if (flag) {
            sendLogService.delete(sendLogId);
            sendLogService.batchInsert(sendLogs);
        } else {
            //如果有一个成功的
            sendLogService.update(sendLog);
            sendLogService.batchInsert(sendLogs);
        }
    }

    /**
     * 渲染模板数据
     *
     * @param resource 模板内容
     * @param data    填充数据对象
     * @return 渲染后的数据
     */
    private String produceContent(String resource, Map<String, Object> data) {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.STRING));
        Template template = engine.getTemplate(resource);
        return template.render(data);
    }

}
