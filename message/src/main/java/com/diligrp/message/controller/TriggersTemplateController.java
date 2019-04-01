package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.service.TriggersTemplateService;
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
 * This file was generated on 2019-03-31 10:53:25.
 */
@Api("/triggersTemplate")
@Controller
@RequestMapping("/triggersTemplate")
public class TriggersTemplateController {
    @Autowired
    TriggersTemplateService triggersTemplateService;

    @ApiOperation("跳转到TriggersTemplate页面")
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "triggersTemplate/index";
    }

    @ApiOperation(value="查询TriggersTemplate", notes = "查询TriggersTemplate，返回列表信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="TriggersTemplate", paramType="form", value = "TriggersTemplate的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<TriggersTemplate> list(@ModelAttribute TriggersTemplate triggersTemplate) {
        return triggersTemplateService.list(triggersTemplate);
    }

    @ApiOperation(value="分页查询TriggersTemplate", notes = "分页查询TriggersTemplate，返回easyui分页信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="TriggersTemplate", paramType="form", value = "TriggersTemplate的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute TriggersTemplate triggersTemplate) throws Exception {
        return triggersTemplateService.listEasyuiPageByExample(triggersTemplate, true).toString();
    }

    @ApiOperation("新增TriggersTemplate")
    @ApiImplicitParams({
		@ApiImplicitParam(name="TriggersTemplate", paramType="form", value = "TriggersTemplate的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(@ModelAttribute TriggersTemplate triggersTemplate) {
        triggersTemplateService.insertSelective(triggersTemplate);
        return BaseOutput.success("新增成功");
    }

    @ApiOperation("修改TriggersTemplate")
    @ApiImplicitParams({
		@ApiImplicitParam(name="TriggersTemplate", paramType="form", value = "TriggersTemplate的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(@ModelAttribute TriggersTemplate triggersTemplate) {
        triggersTemplateService.updateSelective(triggersTemplate);
        return BaseOutput.success("修改成功");
    }

    @ApiOperation("删除TriggersTemplate")
    @ApiImplicitParams({
		@ApiImplicitParam(name="id", paramType="form", value = "TriggersTemplate的主键", required = true, dataType = "long")
	})
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        triggersTemplateService.delete(id);
        return BaseOutput.success("删除成功");
    }
}