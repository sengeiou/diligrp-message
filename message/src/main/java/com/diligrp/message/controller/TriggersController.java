package com.diligrp.message.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.service.TriggersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    private TriggersService triggersService;

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
        return triggersService.listPageForEasyui(triggers, true).toString();
    }

    @ApiOperation("保存Triggers")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Triggers", paramType="form", value = "Triggers的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/save.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput save(String jsonParams) {
        TriggersVo triggersVo = JSONObject.parseObject(jsonParams, TriggersVo.class);

        return BaseOutput.success("新增成功");
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

    @ApiOperation("跳转到Triggers新增页面")
    @RequestMapping(value="/add.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(ModelMap modelMap, @RequestParam(name="id", required = false) Long id) throws Exception {
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        if (userTicket == null) {
            throw new RuntimeException("未登录");
        }
        return "triggers/edit";
    }

    @ApiOperation(value = "Triggers的禁启用", notes = "Triggers的禁启用")
    @RequestMapping(value = "/doEnable.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput doEnable(Long id,Boolean enable){
        return triggersService.updateEnable(id,enable);
    }

    @ApiOperation(value = "Triggers中检查市场-系统-场景对应的关系是否存在", notes = "检查市场-系统-场景对应的关系是否存在")
    @RequestMapping(value = "/checkNotExist.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput checkNotExist(String marketCode,String systemCode,String sceneCode){
        Boolean notExist = triggersService.checkNotExist(marketCode, systemCode, sceneCode);
        if (notExist) {
            return BaseOutput.success();
        } else {
            return BaseOutput.failure();
        }
    }
}