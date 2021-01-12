package com.diligrp.message.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.metadata.ValueProviderUtils;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.domain.input.TriggersSaveInput;
import com.diligrp.message.domain.input.TriggersTemplateSaveInput;
import com.diligrp.message.service.MarketChannelService;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.TriggersTemplateService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final ValueProviderUtils valueProviderUtils;

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
        if (Objects.nonNull(id)) {
            Triggers triggers = triggersService.get(id);
            if (null != triggers) {
                TriggersSaveInput triggersSaveInput = BeanUtil.copyProperties(triggers, TriggersSaveInput.class);
                List<TriggersTemplate> triggersTemplates = triggersTemplateService.selectByTriggerCode(triggers.getTriggerCode());
                if (CollectionUtil.isNotEmpty(triggersTemplates)) {
                    List<TriggersTemplateSaveInput> triggersTemplateSaveInputList = Lists.newArrayListWithExpectedSize(triggersTemplates.size());
                    triggersTemplates.forEach(t->{
                        TriggersTemplateSaveInput input = BeanUtil.copyProperties(t,TriggersTemplateSaveInput.class);
                        input.setAccessKeyIds(StreamEx.of(t.getMarketChannelIds().split(",")).mapToLong(Long::parseLong).toArray());
                        triggersTemplateSaveInputList.add(input);
                    });
                    triggersSaveInput.setTemplateList(triggersTemplateSaveInputList);
                }
                modelMap.put("triggers", JSONUtil.toJsonStr(triggersSaveInput));
                return "triggers/edit";
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

    /**
     * 根据市场code 及 通道 获取配置的 通道key
     * @param marketCode 市场编码
     * @param channel 通道
     * @return
     */
    @RequestMapping(value = "/getChannelKey.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput getChannelKey(String marketCode,String channel){
        MarketChannel marketChannel = new MarketChannel();
        marketChannel.setMarketCode(marketCode);
        marketChannel.setChannel(channel);
        return BaseOutput.success().setData(marketChannelService.list(marketChannel));
    }

    /**
     * 查看模板设置详情
     * @param id 数据ID
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/view.action")
    public String view(Long id,ModelMap modelMap) {
        if (Objects.nonNull(id)) {
            Triggers triggers = triggersService.get(id);
            if (null != triggers) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(triggers));
                List<TriggersTemplate> triggersTemplates = triggersTemplateService.selectByTriggerCode(triggers.getTriggerCode());
                if (CollectionUtil.isNotEmpty(triggersTemplates)) {
                    List<JSONObject> triggersTemplateList = Lists.newArrayListWithExpectedSize(triggersTemplates.size());
                    triggersTemplates.forEach(t -> {
                        JSONObject templateObject = JSONObject.parseObject(JSONObject.toJSONString(t));
                        Set<Long> collect = StreamEx.of(t.getMarketChannelIds().split(",")).map(Long::parseLong).collect(Collectors.toSet());
                        List<MarketChannel> marketChannelList = marketChannelService.queryByIds(collect);
                        if (CollectionUtil.isNotEmpty(marketChannelList)) {
                            String accessKeyValue = marketChannelList.stream().map(m -> m.getAccessKey()).collect(Collectors.joining(", "));
                            templateObject.put("accessKeyValue", accessKeyValue);
                        }
                        templateObject.put("channel", MessageEnum.ChannelEnum.getInstance(t.getChannel()).getName());
                        triggersTemplateList.add(templateObject);
                    });
                    jsonObject.put("templateList", triggersTemplateList);
                }
                modelMap.put("triggers", jsonObject.toJSONString());
            }
        }
        return "triggers/view";
    }
}