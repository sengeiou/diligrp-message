package com.diligrp.message.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.input.WhitelistSaveInfo;
import com.diligrp.message.domain.vo.WhitelistVo;
import com.diligrp.message.service.WhitelistService;
import com.diligrp.message.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/whitelist")
public class WhitelistController {

    private final WhitelistService whitelistService;

    /**
     * 白名单首页
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "whitelist/list";
    }


    /**
     * 查询白名单列表
     * @param whitelist
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String listPage(@RequestBody WhitelistVo whitelist) throws Exception {
        return whitelistService.findByWhitelistVo(whitelist, true).toString();
    }

    /**
     * 进入白名单新增页面
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAdd.action", method = {RequestMethod.GET})
    public String toAdd(ModelMap modelMap) {
        return "whitelist/add";
    }

    /**
     * 新增白名单数据
     * @param whitelistInfo
     * @return
     */
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public  BaseOutput insert(@RequestBody WhitelistSaveInfo whitelistInfo) {
        Whitelist whitelist = BeanUtil.copyProperties(whitelistInfo,Whitelist.class);
        whitelist.setSource(String.valueOf(MessageEnum.WhitelistSourceEnum.MANUAL.getCode()));
        whitelist.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
        //结束时间 设置为 23:59:59
        whitelist.setEndDateTime(whitelistInfo.getEndDate().atTime(23, 59, 59));
        //开始时间 设置为 00:00:00
        whitelist.setStartDateTime(whitelistInfo.getStartDate().atTime(0,0,0));
        if (whitelistService.checkDate(whitelist)) {
            return BaseOutput.failure("新增失败！该时间段与已有时间段存在重复。");
        }
        whitelistService.saveWhitelist(whitelist);
        return BaseOutput.success("新增成功");
    }

    /**
     * 删除白名单数据
     * @param id 数据ID
     * @return
     */
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public  BaseOutput delete(Long id) {
        Whitelist whitelist = whitelistService.get(id);
        whitelist.setDeleted(MessageEnum.DeletedEnum.YES.getCode());
        whitelist.setModified(MessageUtil.now());
        whitelistService.updateSelective(whitelist);
        return BaseOutput.success("删除成功");
    }
}