package com.diligrp.message.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.service.MarketChannelService;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.TriggersTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 * @author yuehongbo
 */
@Controller
@RequestMapping("/triggers")
public class TriggersController {

    @Autowired
    private TriggersService triggersService;
    @Autowired
    private MarketChannelService marketChannelService;
    @Autowired
    private TriggersTemplateService triggersTemplateService;

    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "triggers/index";
    }

    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<Triggers> list(@ModelAttribute Triggers triggers) {
        return triggersService.list(triggers);
    }

    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute Triggers triggers) throws Exception {
        return triggersService.listPageForEasyui(triggers, true).toString();
    }

    @RequestMapping(value="/save.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput save(String jsonParams) {
        TriggersVo triggersVo = JSONObject.parseObject(jsonParams, TriggersVo.class);
        return triggersService.saveInfo(triggersVo);
    }

    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        triggersService.delete(id);
        return BaseOutput.success("删除成功");
    }

    @RequestMapping(value="/toEdit.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String toEdit(ModelMap modelMap, @RequestParam(name="id", required = false) Long id) throws Exception {
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        if (userTicket == null) {
            throw new RuntimeException("未登录");
        }
        modelMap.put("isFirst","true");
        if (null != id){
            Triggers triggers = triggersService.get(id);
            if (null != triggers){
                modelMap.put("triggers",triggers);
                List<TriggersTemplate> triggersTemplates = triggersTemplateService.selectByTriggerCode(triggers.getTriggerCode());
                if (CollectionUtil.isNotEmpty(triggersTemplates)){
                    modelMap.put("triggersTemplateList",triggersTemplates);
                }
            }
        }
        return "triggers/edit";
    }

    @RequestMapping(value = "/doEnable.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput doEnable(Long id,Boolean enable){
        return triggersService.updateEnable(id,enable);
    }

    @RequestMapping(value = "/checkNotExist.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput checkNotExist(String marketCode,String systemCode,String sceneCode,Long selfId){
        Boolean notExist = triggersService.checkNotExist(marketCode, systemCode, sceneCode,selfId);
        if (notExist) {
            return BaseOutput.success();
        } else {
            return BaseOutput.failure();
        }
    }

    @RequestMapping(value = "/getChannelKey.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput getChannelKey(String marketCode,String channel){
        MarketChannel marketChannel = new MarketChannel();
        marketChannel.setMarketCode(marketCode);
        marketChannel.setChannel(channel);
        return BaseOutput.success().setData(marketChannelService.list(marketChannel));
    }

    @RequestMapping(value="/toDetail.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String toDetail(ModelMap modelMap, @RequestParam(name="id") Long id) {
        if (null == id) {
            throw new IllegalArgumentException("参数异常");
        }
        if (null != id) {
            Triggers triggers = triggersService.get(id);
            if (null != triggers) {
                modelMap.put("triggers", triggers);
                List<TriggersTemplate> triggersTemplates = triggersTemplateService.selectByTriggerCode(triggers.getTriggerCode());
                if (CollectionUtil.isNotEmpty(triggersTemplates)) {
                    modelMap.put("triggersTemplateList", triggersTemplates);
                }
            }
        }
        return "triggers/view";
    }
}