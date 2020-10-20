package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.SendLogVo;
import com.diligrp.message.service.SendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-03 15:57:14.
 */
@Controller
@RequestMapping("/sendLog")
public class SendLogController {
    @Autowired
    SendLogService sendLogService;

    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "sendLog/index";
    }

    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<SendLog> list(@ModelAttribute SendLog sendLog) {
        return sendLogService.list(sendLog);
    }

    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute SendLogVo sendLog) throws Exception {
        return sendLogService.findBySendLogVo(sendLog, true).toString();
    }

    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(@ModelAttribute SendLog sendLog) {
        sendLogService.insertSelective(sendLog);
        return BaseOutput.success("新增成功");
    }

    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(@ModelAttribute SendLog sendLog) {
        sendLogService.updateSelective(sendLog);
        return BaseOutput.success("修改成功");
    }

    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        sendLogService.delete(id);
        return BaseOutput.success("删除成功");
    }
}