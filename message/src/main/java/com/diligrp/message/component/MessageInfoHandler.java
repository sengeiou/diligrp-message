package com.diligrp.message.component;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.vo.MessageInfoVo;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.service.SendLogService;
import com.diligrp.message.service.TriggersService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TriggersService triggersService;
    @Autowired
    private MessageSendTask messageSendTask;
    @Autowired
    private SendLogService sendLogService;

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
        if (CollectionUtil.isEmpty(triggersVo.getTemplateList())) {
            msg.append("模板未配置 ");
        }
        SendLog sendLog = new SendLog();
        sendLog.setMarketCode(info.getMarketCode());
        sendLog.setSystemCode(info.getSystemCode());
        sendLog.setSceneCode(info.getSceneCode());
        sendLog.setReceiptTime(new Date());
        sendLog.setRemarks(msg.toString());
        sendLog.setParameters(info.getParameters().toJSONString());
        if (StrUtil.isNotBlank(msg)){
            sendLog.setSendState(MessageEnum.SendStateEnum.FAILURE.getCode());
            sendLogService.save(sendLog);
            return BaseOutput.failure().setResult(msg.toString());

        }else{
            sendLog.setSendState(MessageEnum.SendStateEnum.WAITING.getCode());
            sendLogService.save(sendLog);
            //目前只有短信，则直接注册到短信任务中
            messageSendTask.registerSMS(sendLog.getId(),new Date(),triggersVo.getTemplateList());
            return BaseOutput.success();
        }

    }
}
