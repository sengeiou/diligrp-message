package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.service.TriggersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 * @author yuehongbo
 */
@Api("/triggers")
@Controller
@RequestMapping("/triggers")
public class TriggersController {
    @Autowired
    TriggersService triggersService;

    @ApiOperation("跳转到Triggers页面")
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "triggers/index";
    }

    @ApiOperation(value="查询Triggers", notes = "查询Triggers，返回列表信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Triggers", paramType="form", value = "Triggers的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<Triggers> list(@ModelAttribute Triggers triggers) {
        return triggersService.list(triggers);
    }

    @ApiOperation(value="分页查询Triggers", notes = "分页查询Triggers，返回easyui分页信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Triggers", paramType="form", value = "Triggers的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute Triggers triggers) throws Exception {
        return triggersService.listEasyuiPageByExample(triggers, true).toString();
    }

    @ApiOperation("新增Triggers")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Triggers", paramType="form", value = "Triggers的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(@ModelAttribute Triggers triggers) {
        triggersService.insertSelective(triggers);
        return BaseOutput.success("新增成功");
    }

    @ApiOperation("修改Triggers")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Triggers", paramType="form", value = "Triggers的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(@ModelAttribute Triggers triggers) {
        triggersService.updateSelective(triggers);
        return BaseOutput.success("修改成功");
    }

    @ApiOperation("删除Triggers")
    @ApiImplicitParams({
		@ApiImplicitParam(name="id", paramType="form", value = "Triggers的主键", required = true, dataType = "long")
	})
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        triggersService.delete(id);
        return BaseOutput.success("删除成功");
    }
}