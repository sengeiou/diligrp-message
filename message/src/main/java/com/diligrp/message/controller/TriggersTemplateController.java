package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.service.TriggersTemplateService;
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
 * This file was generated on 2019-03-31 10:53:25.
 */
@Controller
@RequestMapping("/triggersTemplate")
public class TriggersTemplateController {
    @Autowired
    TriggersTemplateService triggersTemplateService;

    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "triggersTemplate/index";
    }

    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<TriggersTemplate> list(@ModelAttribute TriggersTemplate triggersTemplate) {
        return triggersTemplateService.list(triggersTemplate);
    }

    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute TriggersTemplate triggersTemplate) throws Exception {
        return triggersTemplateService.listEasyuiPageByExample(triggersTemplate, true).toString();
    }

    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(@ModelAttribute TriggersTemplate triggersTemplate) {
        triggersTemplateService.insertSelective(triggersTemplate);
        return BaseOutput.success("新增成功");
    }

    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(@ModelAttribute TriggersTemplate triggersTemplate) {
        triggersTemplateService.updateSelective(triggersTemplate);
        return BaseOutput.success("修改成功");
    }

    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        triggersTemplateService.delete(id);
        return BaseOutput.success("删除成功");
    }
}