package com.diligrp.message.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.domain.input.TriggersSaveInput;
import com.diligrp.message.service.MarketChannelService;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.TriggersTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 * @author yuehongbo
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/triggers")
public class TriggersController {

    private final TriggersService triggersService;
    private final MarketChannelService marketChannelService;
    private final TriggersTemplateService triggersTemplateService;

    /**
     * 场景模板首页
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "triggers/list";
    }


    /**
     * 分页查询场景模板信息
     * @param triggers
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/listPage.action")
    @ResponseBody
    public String listPage(@RequestBody Triggers triggers) throws Exception {
        return triggersService.listPageForEasyui(triggers, true).toString();
    }


    /**
     * 进入消息触发预编辑页面
     * @param id 数据ID
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/preSave.action")
    public String preSave(Long id, ModelMap modelMap) {
        modelMap.put("isFirst","true");
        if (Objects.nonNull(id)){
            Triggers triggers = triggersService.get(id);
            if (null != triggers){
                modelMap.put("triggers",triggers);
                List<TriggersTemplate> triggersTemplates = triggersTemplateService.selectByTriggerCode(triggers.getTriggerCode());
                if (CollectionUtil.isNotEmpty(triggersTemplates)){
                    modelMap.put("triggersTemplateList",triggersTemplates);
                }
            }
        }
        return "triggers/add";
    }


    @PostMapping(value = "/save.action")
    @ResponseBody
    public BaseOutput save(@RequestBody TriggersSaveInput saveInput) {
        return triggersService.saveInfo(saveInput);
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