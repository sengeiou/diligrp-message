package com.diligrp.message.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.commons.glossary.YesOrNoEnum;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.common.enums.TriggersEnum;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.sdk.domain.input.MessageInfoInput;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.WhitelistService;
import com.diligrp.message.utils.MessageUtil;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
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

    /**
     * 处理接收到的信息
     * @param info
     */
    public BaseOutput handler(MessageInfoInput info){
        BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(info);
        SendLog sendLog = new SendLog();
        sendLog.setMarketCode(info.getMarketCode());
        sendLog.setSystemCode(info.getSystemCode());
        sendLog.setSceneCode(info.getSceneCode());
        sendLog.setCellphone(info.getCellphone());
        sendLog.setReceiptTime(MessageUtil.now());
        sendLog.setTemplateCode(info.getTemplateCode());
        sendLog.setSendTime(MessageUtil.now());
        sendLog.setRemoteIp(info.getIp());
        if (StrUtil.isNotBlank(info.getParameters())){
            sendLog.setParameters(info.getParameters());
        }
        if (beanValidationResult.isSuccess()) {
            TriggersVo triggers = new TriggersVo();
            triggers.setMarketCode(info.getMarketCode());
            triggers.setSystemCode(info.getSystemCode());
            triggers.setSceneCode(info.getSceneCode());
            List<TriggersVo> templateList = triggersService.selectForUnionTemplate(triggers);
            //存储存在于白名单中的手机号
            Set<String> whiteSet = Sets.newHashSet();
            //存在未在白名单中的手机号
            Set<String> notWhiteSet = Sets.newHashSet();
            StringBuffer msg = new StringBuffer();
            TriggersVo triggersVo = new TriggersVo();
            if (CollectionUtil.isEmpty(templateList)) {
                msg.append("应用未配置 ");
            } else {
                triggersVo = templateList.get(0);
                if (TriggersEnum.EnabledStateEnum.DISABLED.getCode().equals(triggersVo.getEnabled())) {
                    msg.append("应用场景已禁用 ");
                } else if (CollectionUtil.isEmpty(triggersVo.getTemplateList())) {
                    msg.append("模板未配置 ");
                } else if (YesOrNoEnum.YES.getCode().equals(triggersVo.getWhitelist())) {
                    String[] phones = info.getCellphone().split(",");
                    //如果需要验证白名单，检查用户是否存在白名单中
                    Whitelist whitelist = new Whitelist();
                    whitelist.setMarketCode(info.getMarketCode());
                    Set<String> dbData = whitelistService.queryValidByMarketCode(whitelist);
                    if (CollectionUtil.isNotEmpty(dbData)) {
                        for (String phone : phones) {
                            if (dbData.contains(phone)) {
                                whiteSet.add(phone);
                            } else {
                                notWhiteSet.add(phone);
                            }
                        }
                        if (CollectionUtil.isEmpty(whiteSet)) {
                            msg.append("所属手机未在白名单内");
                        } else {
                            info.setCellphone(StrUtil.join(",", whiteSet));
                        }
                    } else {
                        msg.append("所属手机都未在白名单内");
                    }
                }
                if (StrUtil.isNotBlank(info.getTemplateCode()) && CollectionUtil.isNotEmpty(triggersVo.getTemplateList())) {
                    Boolean matched = triggersVo.getTemplateList().stream().anyMatch(t -> StrUtil.isNotBlank(t.getTemplateCode()) && info.getTemplateCode().trim().equalsIgnoreCase(t.getTemplateCode().trim()));
                    if (!matched) {
                        msg.append("指定的模板未在该场景中配置 ");
                    }
                }
            }
            if (StrUtil.isNotBlank(msg)) {
                sendLog.setRemarks(msg.toString());
                sendLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
                sendLogService.save(sendLog);
                log.warn(String.format("信息[%s]-->发送失败[%s]", JSONObject.toJSONString(sendLog), msg));
                return BaseOutput.failure().setMessage(msg.toString()).setMetadata(sendLog.getRequestCode());
            } else {
                if (messageSend) {
                    if (CollectionUtil.isNotEmpty(notWhiteSet)) {
                        SendLog log = new SendLog();
                        BeanUtil.copyProperties(sendLog, log);
                        log.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
                        log.setCellphone(StrUtil.join(",", notWhiteSet));
                        log.setRemarks("此部分手机不在白名单中");
                        sendLogService.save(log);
                    }
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
                return BaseOutput.success();
            }
        } else {
            List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
            String collect = errorMessages.stream().map(t -> t.getMessage()).collect(Collectors.joining());
            sendLog.setRemarks(collect);
            sendLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
            sendLogService.save(sendLog);
            log.warn(String.format("信息[%s]-->发送失败[%s]", JSONObject.toJSONString(sendLog), collect));
            return BaseOutput.failure().setMessage(collect).setMetadata(sendLog.getRequestCode());
        }
    }
}
