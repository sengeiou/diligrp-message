package com.diligrp.message.component;

import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.vo.MessageInfoVo;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.service.TriggersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    /**
     * 处理接收到的信息
     * @param info
     */
    public void handler(MessageInfoVo info){
        Triggers triggers = new Triggers();
        triggers.setMarketCode(info.getMarketCode());
        triggers.setSystemCode(info.getSystemCode());
        triggers.setSceneCode(info.getSceneCode());
        List<TriggersVo> templateList = triggersService.selectForUnionTemplate(triggers);

    }
}
