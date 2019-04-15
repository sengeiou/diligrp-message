package com.diligrp.message.component;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.common.enums.TriggersEnum;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.vo.MessageInfoVo;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.WhitelistService;
import com.diligrp.message.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * <B>处理接收到的信息推送请求</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 11:18
 */
@Component
public class MessageInfoHandler {

    @Value("${message.enable}")
    private Boolean messageSend;

    @Autowired
    private TriggersService triggersService;
    @Autowired
    private MessageSendTask messageSendTask;
    @Autowired
    private SendLogService sendLogService;
    @Autowired
    private WhitelistService whitelistService;

    /**
     * 处理接收到的信息
     * @param info
     */
    public BaseOutput handler(MessageInfoVo info){
        Triggers triggers = new Triggers();
        triggers.setMarketCode(info.getMarketCode());
        triggers.setSystemCode(info.getSystemCode());
        triggers.setSceneCode(info.getSceneCode());
        List<TriggersVo> templateList = triggersService.selectForUnionTemplate(triggers);
        StringBuffer msg = new StringBuffer();
        if (CollectionUtil.isEmpty(templateList)) {
            msg.append("应用未配置 ");
        }
        TriggersVo triggersVo = templateList.get(0);
        if(TriggersEnum.EnabledStateEnum.DISABLED.getCode().equals(triggersVo.getEnabled())){
            msg.append("应用场景已禁用 ");
        }else if (CollectionUtil.isEmpty(triggersVo.getTemplateList())) {
            msg.append("模板未配置 ");
        }else if (triggersVo.getWhitelist()){
            //如果需要验证白名单，检查用户是否存在白名单中
            Whitelist whitelist = new Whitelist();
            whitelist.setMarketCode(info.getMarketCode());
            whitelist.setCellphone(info.getCellphone());
            Integer integer = whitelistService.queryValidByMarketCode(whitelist);
            if (integer < 1) {
                msg.append("此用户不在该场景的白名单中");
            }
        }
        SendLog sendLog = new SendLog();
        sendLog.setMarketCode(info.getMarketCode());
        sendLog.setSystemCode(info.getSystemCode());
        sendLog.setSceneCode(info.getSceneCode());
        sendLog.setCellphone(info.getCellphone());
        sendLog.setReceiptTime(new Date());
        sendLog.setRemarks(msg.toString());
        sendLog.setParameters(info.getParameters().toJSONString());
        if (StrUtil.isNotBlank(msg)){
            sendLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
            sendLogService.save(sendLog);
            return BaseOutput.failure().setResult(msg.toString());
        }else{
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
                sendLog.setSendState(MessageEnum.SendStateEnum.SUCCEED.getCode());
                sendLogService.save(sendLog);
            }
            return BaseOutput.success();
        }
    }
}
