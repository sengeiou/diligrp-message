package com.diligrp.message;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.diligrp.message.sdk.domain.input.MessageInfoInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2021/3/29 14:41
 */
@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@WebAppConfiguration
public class MessageApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReceiveMessage()  {
        MessageInfoInput messageInfoInput = new MessageInfoInput();
        messageInfoInput.setCellphone("17608176657,13898758957");
        messageInfoInput.setSystemCode("customer");
        messageInfoInput.setMarketCode("group");
        messageInfoInput.setSceneCode("registerAuthCode");
        messageInfoInput.setIp(NetUtil.getLocalhostStr());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", RandomUtil.randomNumbers(6));
        jsonObject.put("phone", PhoneUtil.hideBetween(messageInfoInput.getCellphone()));
        messageInfoInput.setParameters(jsonObject.toJSONString());
        try {
            MvcResult result = mockMvc.perform(post("/messageApi/receiveMessage.api").content(JSONObject.toJSONString(messageInfoInput))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andReturn();
            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
