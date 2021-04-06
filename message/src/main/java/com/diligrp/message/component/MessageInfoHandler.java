package com.diligrp.message.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.commons.glossary.YesOrNoEnum;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.BizNumberTypeEnum;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.common.enums.TriggersEnum;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.sdk.domain.input.MessageInfoInput;
import com.diligrp.message.service.BlacklistService;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.WhitelistService;
import com.diligrp.message.service.remote.UidRpcService;
import com.diligrp.message.utils.MessageUtil;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <B>处理接收到的信息推送请求</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 11:18
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class MessageInfoHandler {

    @Value("${message.enable}")
    private Boolean messageSend;

    private final TriggersService triggersService;
    private final MessageSendTask messageSendTask;
    private final SendLogService sendLogService;
    private final WhitelistService whitelistService;
    private final BlacklistService blacklistService;
    private final UidRpcService uidRpcService;

    /**
     * 处理接收到的信息
     * @param info
     */
    public BaseOutput handler(MessageInfoInput info) {
        BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(info);
        SendLog sendLog = new SendLog();
        Optional<String> bizNumber = uidRpcService.getBizNumber(BizNumberTypeEnum.SEND_REQUEST);
        sendLog.setRequestCode(bizNumber.orElse(IdUtil.getSnowflake(1, 1).nextIdStr()));
        sendLog.setMarketCode(info.getMarketCode());
        sendLog.setBusinessMarketCode(info.getBusinessMarketCode());
        sendLog.setSystemCode(info.getSystemCode());
        sendLog.setSceneCode(info.getSceneCode());
        sendLog.setCellphone(info.getCellphone());
        sendLog.setReceiptTime(MessageUtil.now());
        sendLog.setTemplateCode(info.getTemplateCode());
        sendLog.setSendTime(MessageUtil.now());
        sendLog.setRemoteIp(info.getIp());
        if (StrUtil.isNotBlank(info.getParameters())) {
            sendLog.setParameters(info.getParameters());
        }
        if (beanValidationResult.isSuccess()) {
            TriggersVo triggers = new TriggersVo();
            triggers.setMarketCode(info.getMarketCode());
            triggers.setSystemCode(info.getSystemCode());
            triggers.setSceneCode(info.getSceneCode());
            List<TriggersVo> templateList = triggersService.selectForUnionTemplate(triggers);
            //存储最终需要发送短信的手机号
            Set<String> finalPhoneSet = Sets.newHashSet();
            StringBuffer msg = new StringBuffer();
            TriggersVo triggersVo = new TriggersVo();
            if (CollectionUtil.isEmpty(templateList)) {
                msg.append(" 应用未配置 ");
            } else {
                triggersVo = templateList.get(0);
                if (TriggersEnum.EnabledStateEnum.DISABLED.getCode().equals(triggersVo.getEnabled())) {
                    msg.append(" 应用场景已禁用 ");
                } else if (CollectionUtil.isEmpty(triggersVo.getTemplateList())) {
                    msg.append(" 模板未配置 ");
                } else {
                    /**
                     * 以下逻辑为判断传入的手机号，是否在黑白名单中
                     * 如果场景启用了黑名单并且手机号在黑名单中，则该手机号不会发送短信
                     * 如果场景启用了白名单并且手机号不在白名单中，则该手机号不会发送短信
                     */
                    StringBuffer resultMsg = new StringBuffer();
                    Collections.addAll(finalPhoneSet, info.getCellphone().split(","));
                    if (YesOrNoEnum.YES.getCode().equals(triggersVo.getBlacklist())) {
                        Set<String> blacklistSet = blacklistService.queryValidByMarketCode(info.getBusinessMarketCode());
                        //取出两个集合的交集
                        blacklistSet.retainAll(finalPhoneSet);
                        //从原始数据中移除存在于黑名单中的数据
                        finalPhoneSet.removeAll(blacklistSet);
                        if (CollectionUtil.isEmpty(finalPhoneSet)) {
                            resultMsg.append(" 手机号:").append(info.getCellphone()).append("全部存在于黑名单中");
                        } else {
                            resultMsg.append(" 手机号:").append(StrUtil.join(",", blacklistSet)).append("存在于黑名单中");
                        }
                    }
                    if (YesOrNoEnum.YES.getCode().equals(triggersVo.getWhitelist())) {
                        if (CollectionUtil.isNotEmpty(finalPhoneSet)) {
                            //如果需要验证白名单，检查用户是否存在白名单中
                            Set<String> dbData = whitelistService.queryValidByMarketCode(info.getBusinessMarketCode());
                            //取白名单数据与传入数据的交集
                            Set<String> tempSet = Sets.newHashSet(finalPhoneSet);
                            finalPhoneSet.retainAll(dbData);
                            //从传入数据中移除交集(白名单)数据，剩下的数据即未在白名单中的数据
                            tempSet.removeAll(finalPhoneSet);
                            if (CollectionUtil.isNotEmpty(tempSet)) {
                                resultMsg.append(" 手机号:").append(StrUtil.join(",", tempSet)).append("在白名单中不存在");
                            }
                        }
                    }
                    msg.append(resultMsg);
                }
                if (StrUtil.isNotBlank(info.getTemplateCode()) && CollectionUtil.isNotEmpty(triggersVo.getTemplateList())) {
                    Boolean matched = triggersVo.getTemplateList().stream().anyMatch(t -> StrUtil.isNotBlank(t.getTemplateCode()) && info.getTemplateCode().trim().equalsIgnoreCase(t.getTemplateCode().trim()));
                    if (!matched) {
                        finalPhoneSet = null;
                        msg.delete(0, msg.length());
                        msg.append(" 指定的模板未在该场景中配置 ");
                    }
                }
            }
            if (StrUtil.isNotBlank(msg)) {
                SendLog saveData = new SendLog();
                BeanUtil.copyProperties(sendLog, saveData);
                saveData.setRemarks(msg.toString());
                saveData.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
                sendLogService.save(saveData);
                log.warn(String.format("信息[%s]-->发送失败[%s]", JSONObject.toJSONString(sendLog), saveData.getRemarks()));
                if (CollectionUtil.isEmpty(finalPhoneSet)) {
                    return BaseOutput.failure(sendLog.getRemarks()).setMetadata(sendLog.getRequestCode());
                }
            }
            if (CollectionUtil.isNotEmpty(finalPhoneSet)) {
                sendLog.setCellphone(StrUtil.join(",", finalPhoneSet));
                if (messageSend) {
                    sendLog.setSendState(MessageEnum.SendStateEnum.WAITING.getCode());
                    sendLogService.save(sendLog);
                    //目前只有短信，则直接注册到短信任务中
                    messageSendTask.registerSMS(sendLog.getId(), new Date(), triggersVo.getTemplateList());
                } else {
                    //如果配置的该环境不需要发送短信，则直接记录发送信息
                    String content = MessageUtil.produceMsgContent(triggersVo.getTemplateList().get(0).getTemplateContent(), JSONObject.parseObject(sendLog.getParameters()));
                    sendLog.setContent(content);
                    sendLog.setRemarks("该环境已配置禁用短信发送");
                    sendLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
                    sendLogService.save(sendLog);
                    log.warn(String.format("信息[%s]-->发送失败[%s]", JSONObject.toJSONString(sendLog), sendLog.getRemarks()));
                }
            }
            return BaseOutput.success(sendLog.getRemarks()).setMetadata(sendLog.getRequestCode());
        } else {
            List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
            String collect = errorMessages.stream().map(t -> t.getMessage()).collect(Collectors.joining(","));
            sendLog.setRemarks(collect);
            sendLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
            sendLogService.save(sendLog);
            log.warn(String.format("信息[%s]-->发送失败[%s]", JSONObject.toJSONString(sendLog), collect));
            return BaseOutput.failure().setMessage(collect).setMetadata(sendLog.getRequestCode());
        }
    }
}
