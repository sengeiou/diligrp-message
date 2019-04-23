package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.domain.vo.MarketChannelVo;
import com.diligrp.message.service.MarketChannelService;
import com.diligrp.message.service.TriggersTemplateService;
import com.diligrp.message.service.remote.FirmService;
import com.diligrp.message.utils.Base64Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
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
 * This file was generated on 2019-04-09 15:57:04.
 */
@Api("/marketChannel")
@Controller
@RequestMapping("/marketChannel")
public class MarketChannelController {
    @Autowired
    MarketChannelService marketChannelService;
    @Autowired
    TriggersTemplateService triggersTemplateService;
    @Autowired
    FirmService firmService;

    @ApiOperation("跳转到MarketChannel页面")
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "marketChannel/index";
    }

    @ApiOperation(value="查询MarketChannel", notes = "查询MarketChannel，返回列表信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="MarketChannel", paramType="form", value = "MarketChannel的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<MarketChannel> list(@ModelAttribute MarketChannel marketChannel) {
        return marketChannelService.list(marketChannel);
    }

    @ApiOperation(value="分页查询MarketChannel", notes = "分页查询MarketChannel，返回easyui分页信息")
    @ApiImplicitParams({
		@ApiImplicitParam(name="MarketChannel", paramType="form", value = "MarketChannel的form信息", required = false, dataType = "string")
	})
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute MarketChannelVo marketChannelVo) throws Exception {
        marketChannelVo.setAuthMarkets(firmService.getCurrentUserFirmCodes());
        return marketChannelService.listEasyuiPageByExample(marketChannelVo, true).toString();
    }

    @ApiOperation("新增MarketChannel")
    @ApiImplicitParams({
		@ApiImplicitParam(name="MarketChannel", paramType="form", value = "MarketChannel的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(@ModelAttribute MarketChannel marketChannel) throws Exception{
        marketChannel.setSecret(Base64Util.getEncoderString(marketChannel.getSecret()));
        marketChannelService.insertSelective(marketChannel);
        return BaseOutput.success("新增成功");
    }

    @ApiOperation("修改MarketChannel")
    @ApiImplicitParams({
		@ApiImplicitParam(name="MarketChannel", paramType="form", value = "MarketChannel的form信息", required = true, dataType = "string")
	})
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(@ModelAttribute MarketChannel marketChannel) throws Exception{
        marketChannel.setSecret(Base64Util.getEncoderString(marketChannel.getSecret()));
        if (marketChannel.getCompanyName() == null){
            marketChannel.setCompanyName("");
        }
        marketChannelService.updateSelective(marketChannel);
        return BaseOutput.success("修改成功");
    }

    @ApiOperation("删除MarketChannel")
    @ApiImplicitParams({
		@ApiImplicitParam(name="id", paramType="form", value = "MarketChannel的主键", required = true, dataType = "long")
	})
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        if (id == null){
            return BaseOutput.failure("删除失败！参数ID为空");
        }
        List<TriggersTemplate> list = triggersTemplateService.listByMarketChannelId(String.valueOf(id));
        if (CollectionUtils.isNotEmpty(list)){
            return BaseOutput.failure("删除失败！该通道下还存在消息模板，不能删除！");
        }
        marketChannelService.delete(id);
        return BaseOutput.success("删除成功");
    }
}