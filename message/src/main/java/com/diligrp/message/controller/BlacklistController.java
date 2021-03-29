package com.diligrp.message.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dili.logger.sdk.annotation.BusinessLogger;
import com.dili.logger.sdk.util.LoggerUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.constants.MessageConstant;
import com.diligrp.message.domain.Blacklist;
import com.diligrp.message.domain.input.BlacklistSaveInput;
import com.diligrp.message.domain.query.BlacklistQuery;
import com.diligrp.message.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * 黑名单控制层
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/blacklist")
public class BlacklistController {

    private final BlacklistService blacklistService;

    /**
     * 白名单首页
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "blacklist/list";
    }


    /**
     * 查询黑名单列表
     * @param blacklistQuery
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String listPage(@RequestBody BlacklistQuery blacklistQuery) throws Exception {
        blacklistQuery.setMarketCode(SessionContext.getSessionContext().getUserTicket().getFirmCode());
        return blacklistService.listPageForEasyui(blacklistQuery, true).toString();
    }

    /**
     * 进入白名单新增页面
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAdd.action", method = {RequestMethod.GET})
    public String toAdd(ModelMap modelMap) {
        return "blacklist/add";
    }

    /**
     * 新增白名单数据
     * @param blacklistSaveInput
     * @return
     */
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @BusinessLogger(systemCode = MessageConstant.SYSTEM_CODE, businessType = "message_blacklist", notes = "新增黑名单数据", operationType = "add")
    public BaseOutput insert(@RequestBody BlacklistSaveInput blacklistSaveInput) {
        Blacklist blacklist = BeanUtil.copyProperties(blacklistSaveInput, Blacklist.class);
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        blacklist.setMarketCode(userTicket.getFirmCode());
        blacklist.setCreatorId(userTicket.getId());
        //结束时间 设置为 23:59:59
        blacklist.setEndTime(blacklistSaveInput.getEndDate().atTime(23, 59, 59));
        //开始时间 设置为 00:00:00
        blacklist.setStartTime(blacklistSaveInput.getStartDate().atTime(0, 0, 0));
        if (blacklistService.checkDate(blacklist)) {
            return BaseOutput.failure("新增失败！该时间段与已有时间段存在重复。");
        }
        blacklistService.saveBlacklist(blacklist);
        StringBuffer strb = new StringBuffer();
        strb.append(" 客户姓名：").append(blacklist.getCustomerName());
        strb.append(" 手机号：").append(blacklist.getCellphone());
        strb.append(" 有效期：").append(blacklistSaveInput.getStartDate()).append(" - ").append(blacklistSaveInput.getEndDate());
        LoggerUtil.buildBusinessLoggerContext(blacklist.getId(), String.valueOf(blacklist.getId()), userTicket.getId(), userTicket.getRealName(), userTicket.getFirmId(), strb.toString());
        return BaseOutput.success("新增成功");
    }

    /**
     * 删除白名单数据
     * @param id 数据ID
     * @return
     */
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @BusinessLogger(systemCode = MessageConstant.SYSTEM_CODE, businessType = "message_blacklist", notes = "删除黑名单数据", operationType = "del")
    public BaseOutput delete(Long id) {
        Blacklist blacklist = blacklistService.get(id);
        if (Objects.nonNull(blacklist)) {
            blacklistService.delete(id);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(" 客户姓名: ").append(blacklist.getCustomerName());
            stringBuffer.append(" 客户电话: ").append(blacklist.getCellphone());
            stringBuffer.append(" 有效期：").append(blacklist.getStartTime().toLocalDate()).append(" - ").append(blacklist.getEndTime().toLocalDate());
            UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
            LoggerUtil.buildBusinessLoggerContext(blacklist.getId(), String.valueOf(blacklist.getId()), userTicket.getId(), userTicket.getRealName(), userTicket.getFirmId(), stringBuffer.toString());
        }
        return BaseOutput.success("删除成功");
    }
}