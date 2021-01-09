package com.diligrp.message.service.remote.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.constant.MessagePushConstant;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.service.MarketChannelService;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.remote.DataDictionaryRpcService;
import com.diligrp.message.service.remote.SmsService;
import com.diligrp.message.utils.Base64Util;
import com.diligrp.message.utils.MessageUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 17:47
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SendLogService sendLogService;
    @Autowired
    private MarketChannelService marketChannelService;
    @Resource
    private AlidayuSmsImpl alidayuSmsImpl;
    @Resource
    private ChinaMobileMasImpl chinaMobileMas;
    @Resource
    private SmsChineseImpl smsChinese;
    @Autowired
    private DataDictionaryRpcService dataDictionaryRpcService;

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
        /**
         * 如果模板编码不为空，即此次发送为指定模板发送
         * 所以templateList中需要移除非指定模板的数据
         */
        if (StrUtil.isNotBlank(sendLog.getTemplateCode())) {
            templateList.removeIf(t -> !StrUtil.equalsIgnoreCase(t.getTemplateCode(), sendLog.getTemplateCode()));
        }
        if (CollectionUtil.isEmpty(templateList)){
            sendLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
            sendLog.setRemarks("指定的模板编码未配置");
            sendLogService.update(sendLog);
            return;
        }
        Boolean flag = false;
        List<SendLog> sendLogs = Lists.newArrayList();
        //定义标签，用于跳出循环
        templates:
        for (TriggersTemplate t : templateList) {
            String[] channelId = t.getMarketChannelIds().split(",");
            channel:
            for (String s : channelId) {
                //获取对应的账号
                MarketChannel marketChannel = marketChannelMap.get(Long.valueOf(s));
                if (null != marketChannel) {
                    //根据模板参数等，转换模板内容
                    String content = MessageUtil.produceMsgContent(t.getTemplateContent(), JSONObject.parseObject(sendLog.getParameters()));
                    JSONObject object = new JSONObject();
                    object.put(MessagePushConstant.ACCESS_KEY, marketChannel.getAccessKey());
                    object.put(MessagePushConstant.SECRET, Base64Util.getDecoderString(marketChannel.getSecret()));
                    object.put(MessagePushConstant.SIGN, marketChannel.getSignature());
                    object.put(MessagePushConstant.PHONES, sendLog.getCellphone());
                    object.put(MessagePushConstant.TEMPLATE_CODE, t.getTemplateCode());
                    object.put(MessagePushConstant.PARAMETERS, sendLog.getParameters());
                    object.put(MessagePushConstant.CONTENT, content);
                    BaseOutput<String> output = null;
                    if (t.getChannel().equals(MessageEnum.ChannelEnum.ALI_YUN.getCode())) {
                        output = alidayuSmsImpl.sendSMS(object);
                    } else if (t.getChannel().equals(MessageEnum.ChannelEnum.CHINA_MOBILE.getCode())) {
                        object.put(MessagePushConstant.COMPANY_NAME, marketChannel.getCompanyName());
                        output = chinaMobileMas.sendSMS(object);
                    } else if (t.getChannel().equals(MessageEnum.ChannelEnum.WEBCHINESE_SMS.getCode())) {
                        output = smsChinese.sendSMS(object);
                    }
                    if (Objects.nonNull(output)) {
                        sendLog.setSendTime(MessageUtil.now());
                        sendLog.setRequestId(output.getData());
                        sendLog.setContent(content);
                        if (output.isSuccess()) {
                            sendLog.setBizId(output.getMessage());
                            sendLog.setSendState(MessageEnum.SendStateEnum.SUCCEED.getCode());
                            sendLog.setSendChannel(t.getChannel());
                            sendLog.setRemarks(String.format("账号：%s ，模板code：%s", marketChannel.getAccessKey(), Objects.toString(t.getTemplateCode(), "无")));
                            flag = true;
                            break templates;
                        } else {
                            SendLog tempLog = new SendLog();
                            BeanUtil.copyProperties(sendLog, tempLog);
                            tempLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
                            tempLog.setRemarks(String.format("账号：%s ，错误信息：%s", marketChannel.getAccessKey(), output.getMessage()));
                            tempLog.setId(null);
                            tempLog.setSendChannel(t.getChannel());
                            sendLogs.add(tempLog);
                            if (null != output.getMetadata() && StrUtil.isNotBlank(String.valueOf(output.getMetadata()))) {
                                //邮件可能发送失败，但是不影响主业务，此处忽略邮件是否发送成功
                                try {
                                    MailUtil.send(dataDictionaryRpcService.listToMail(), "消息发送异常", output.getMetadata().toString(), false);
                                } catch (Throwable throwable) {
                                    log.error("邮件通知异常 " + throwable.getMessage(), throwable);
                                }
                            }
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        //如果有一个成功的
        if (flag) {
            sendLogService.update(sendLog);
        } else {
            //如果都没有发送成功
            sendLogService.delete(sendLogId);
        }
        if (CollectionUtil.isNotEmpty(sendLogs)){
            sendLogService.batchInsert(sendLogs);
        }
    }
}
