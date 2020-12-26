package com.diligrp.message.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.SendLogVo;
import com.diligrp.message.service.SendLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-03 15:57:14.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/sendLog")
public class SendLogController {

    private final SendLogService sendLogService;

    /**
     * 去往发送记录首页
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.put("startSendTime", LocalDateTimeUtil.format(LocalDateTimeUtil.beginOfDay(LocalDateTime.now()),"yyyy-MM-dd HH:mm:ss"));
        return "sendLog/list";
    }

    /**
     * 查询发送记录信息
     * @param sendLog
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String listPage(@RequestBody SendLogVo sendLog) throws Exception {
        return sendLogService.findBySendLogVo(sendLog, true).toString();
    }

    /**
     * 查看推送记录详情
     * @param id 记录id
     * @throws Exception
     */
    @RequestMapping(value = "/view.action", method = {RequestMethod.GET, RequestMethod.POST})
    public String view(Long id, ModelMap modelMap) {
        SendLog sendLog = sendLogService.get(id);
        JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(sendLog));
        jsonObject.put("parameters", JSONObject.parse(jsonObject.getString("parameters")));
        modelMap.put("sendLogData", JSONObject.toJSONString(jsonObject));
        return "sendLog/view";
    }
}