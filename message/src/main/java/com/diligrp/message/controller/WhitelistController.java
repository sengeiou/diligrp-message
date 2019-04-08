package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.vo.WhitelistVo;
import com.diligrp.message.service.WhitelistService;
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

import java.util.Calendar;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 */
@Api("/whitelist")
@Controller
@RequestMapping("/whitelist")
public class WhitelistController {
    @Autowired
    WhitelistService whitelistService;

    @ApiOperation("跳转到Whitelist页面")
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "whitelist/index";
    }

    @ApiOperation(value="查询Whitelist", notes = "查询Whitelist，返回列表信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Whitelist", paramType="form", value = "Whitelist的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<Whitelist> list(@ModelAttribute Whitelist whitelist) {
        return whitelistService.list(whitelist);
    }

    @ApiOperation(value="分页查询Whitelist", notes = "分页查询Whitelist，返回easyui分页信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Whitelist", paramType="form", value = "Whitelist的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute WhitelistVo whitelist) throws Exception {
        return whitelistService.findByWhitelistVo(whitelist, true).toString();
    }

    @ApiOperation("新增Whitelist")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Whitelist", paramType="form", value = "Whitelist的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(@ModelAttribute Whitelist whitelist) {
        whitelist.setSource(String.valueOf(MessageEnum.MessageSourceEnum.MANUAL.getCode()));
        whitelist.setDeleted(MessageEnum.DeletedEnum.NO.getCode());

        if (whitelist.getEndDate() != null){
            Calendar c= Calendar.getInstance();
            c.setTime(whitelist.getEndDate());
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            whitelist.setEndDate(c.getTime());//结束时间 的 23:59:59
        }

        if (whitelistService.checkDate(whitelist) ){
            return BaseOutput.failure("新增失败！该时间段已存在，不能重复添加");
        }
        whitelistService.insertSelective(whitelist);
        return BaseOutput.success("新增成功");
    }


    @ApiOperation("修改Whitelist")
    @ApiImplicitParams({
		@ApiImplicitParam(name="Whitelist", paramType="form", value = "Whitelist的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(@ModelAttribute Whitelist whitelist) {
        whitelistService.updateSelective(whitelist);
        return BaseOutput.success("修改成功");
    }

    @ApiOperation("删除Whitelist")
    @ApiImplicitParams({
		@ApiImplicitParam(name="id", paramType="form", value = "Whitelist的主键", required = true, dataType = "long")
	})
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        Whitelist whitelist = whitelistService.get(id);
        whitelist.setDeleted(MessageEnum.DeletedEnum.YES.getCode());
        whitelistService.updateSelective(whitelist);
        return BaseOutput.success("删除成功");
    }
}