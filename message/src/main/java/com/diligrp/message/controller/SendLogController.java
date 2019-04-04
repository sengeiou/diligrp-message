package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.domain.SendLog;
import com.diligrp.message.domain.vo.SendLogVo;
import com.diligrp.message.service.SendLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api("/sendLog")
@Controller
@RequestMapping("/sendLog")
public class SendLogController {
    @Autowired
    SendLogService sendLogService;

    @ApiOperation("跳转到SendLog页面")
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "sendLog/index";
    }

    @ApiOperation(value="查询SendLog", notes = "查询SendLog，返回列表信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="SendLog", paramType="form", value = "SendLog的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<SendLog> list(@ModelAttribute SendLog sendLog) {
        return sendLogService.list(sendLog);
    }

    @ApiOperation(value="分页查询SendLog", notes = "分页查询SendLog，返回easyui分页信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="SendLog", paramType="form", value = "SendLog的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute SendLogVo sendLog) throws Exception {
        return sendLogService.findBySendLogVo(sendLog, true).toString();
    }

    @ApiOperation("新增SendLog")
    @ApiImplicitParams({
		@ApiImplicitParam(name="SendLog", paramType="form", value = "SendLog的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(@ModelAttribute SendLog sendLog) {
        sendLogService.insertSelective(sendLog);
        return BaseOutput.success("新增成功");
    }

    @ApiOperation("修改SendLog")
    @ApiImplicitParams({
		@ApiImplicitParam(name="SendLog", paramType="form", value = "SendLog的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(@ModelAttribute SendLog sendLog) {
        sendLogService.updateSelective(sendLog);
        return BaseOutput.success("修改成功");
    }

    @ApiOperation("删除SendLog")
    @ApiImplicitParams({
		@ApiImplicitParam(name="id", paramType="form", value = "SendLog的主键", required = true, dataType = "long")
	})
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        sendLogService.delete(id);
        return BaseOutput.success("删除成功");
    }
}