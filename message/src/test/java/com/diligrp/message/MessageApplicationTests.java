package com.diligrp.message;

import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.MessageApplication;
import com.diligrp.message.common.constant.MessagePushConstant;
import com.diligrp.message.service.remote.impl.ChinaMobileMasImpl;
import com.diligrp.message.service.remote.impl.SmsChineseImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/11 10:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MessageApplication.class)
public class MessageApplicationTests {

    @Resource
    private SmsChineseImpl smsChinese;

    @Test
    public void testChinaMobileMas(){
        JSONObject object = new JSONObject();
        object.put(MessagePushConstant.ACCESS_KEY, "shenyangdili");
        object.put(MessagePushConstant.SECRET, "4e3412a51439f0c8ce80");
        object.put(MessagePushConstant.SIGN, "地利");
        object.put(MessagePushConstant.PHONES, "17608176657");
        object.put(MessagePushConstant.CONTENT, "你好,验证码为123456");
        BaseOutput<String> output = smsChinese.sendSMS(object);
        System.out.println(output);
    }
}
