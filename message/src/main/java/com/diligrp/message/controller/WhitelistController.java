package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.vo.WhitelistVo;
import com.diligrp.message.service.WhitelistService;
import com.diligrp.message.utils.MessageUtil;
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
 * This file was generated on 2019-03-31 10:54:30.
 */
@Controller
@RequestMapping("/whitelist")
public class WhitelistController {
    @Autowired
    WhitelistService whitelistService;

    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "whitelist/index";
    }

    @RequestMapping(value="/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<Whitelist> list(@ModelAttribute Whitelist whitelist) {
        return whitelistService.list(whitelist);
    }

    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(@ModelAttribute WhitelistVo whitelist) throws Exception {
        return whitelistService.findByWhitelistVo(whitelist, true).toString();
    }

    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(@ModelAttribute Whitelist whitelist) {
        whitelist.setSource(String.valueOf(MessageEnum.MessageSourceEnum.MANUAL.getCode()));
        whitelist.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
        //结束时间 设置为 23:59:59
        whitelist.setEndDateTime(whitelist.getEndDate().atTime(23, 59, 59));
        //开始时间 设置为 00:00:00
        whitelist.setStartDateTime(whitelist.getStartDate().atTime(0,0,0));
        if (whitelistService.checkDate(whitelist)) {
            return BaseOutput.failure("新增失败！该时间段与已有时间段存在重复。");
        }
        whitelistService.saveWhitelist(whitelist);
        return BaseOutput.success("新增成功");
    }


    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(@ModelAttribute Whitelist whitelist) {
        whitelistService.updateSelective(whitelist);
        return BaseOutput.success("修改成功");
    }

    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        Whitelist whitelist = whitelistService.get(id);
        whitelist.setDeleted(MessageEnum.DeletedEnum.YES.getCode());
        whitelist.setModified(MessageUtil.now());
        whitelistService.updateSelective(whitelist);
        return BaseOutput.success("删除成功");
    }
}