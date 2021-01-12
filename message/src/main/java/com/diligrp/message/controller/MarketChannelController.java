package com.diligrp.message.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dili.logger.sdk.annotation.BusinessLogger;
import com.dili.logger.sdk.util.LoggerUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.constants.MessageConstant;
import com.diligrp.message.domain.MarketChannel;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.domain.query.MarketChannelQuery;
import com.diligrp.message.service.MarketChannelService;
import com.diligrp.message.service.TriggersTemplateService;
import com.diligrp.message.service.remote.MarketRpcService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-04-09 15:57:04.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/marketChannel")
public class MarketChannelController {

    private final MarketChannelService marketChannelService;
    private final TriggersTemplateService triggersTemplateService;
    private final MarketRpcService marketRpcService;

    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "marketChannel/list";
    }

    /**
     * 查询市场通道列表信息
     * @param marketChannelQuery
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String listPage(@RequestBody MarketChannelQuery marketChannelQuery) throws Exception {
        marketChannelQuery.setAuthMarkets(marketRpcService.getCurrentUserFirmCodes());
        return marketChannelService.listEasyuiPageByExample(marketChannelQuery, true).toString();
    }

    /**
     * 进入市场通道预编辑页面
     * @param id 数据ID
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/preSave.action", method = {RequestMethod.GET})
    public String preSave(Long id, ModelMap modelMap) {
        if (Objects.nonNull(id)) {
            MarketChannel marketChannel = marketChannelService.get(id);
            marketChannel.setSecret(Base64.decodeStr(marketChannel.getSecret()));
            JSONObject jsonObject = JSONUtil.parseObj(marketChannel);
            modelMap.put("marketChannel", jsonObject);
        } else {
            modelMap.put("marketChannel", "{}");
        }
        return "marketChannel/edit";
    }

    /**
     * 保存市场通道信息
     * @param marketChannel
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/save.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @BusinessLogger(systemCode = MessageConstant.SYSTEM_CODE, businessType = "message_market_channel")
    public BaseOutput save(@RequestBody MarketChannel marketChannel) throws Exception {
        marketChannel.setSecret(Base64.encode(marketChannel.getSecret()));
        marketChannel.setModified(LocalDateTime.now());
        String notes = "修改市场通道信息";
        String prefix = "修改后为:";
        String operationType = "edit";
        if (Objects.isNull(marketChannel.getId())) {
            marketChannel.setCreated(LocalDateTime.now());
            notes = "新增市场通道信息";
            prefix = "";
            operationType = "add";
        }
        marketChannelService.saveOrUpdate(marketChannel);
        UserTicket userTicket = getUserTicket();
        LoggerUtil.buildBusinessLoggerContext(marketChannel.getId(), String.valueOf(marketChannel.getId()), userTicket.getId(), userTicket.getRealName(), userTicket.getFirmId(), buildLoggerContent(marketChannel, prefix).toString(), notes, operationType);
        return BaseOutput.success("保存成功");
    }

    /**
     * 查询市场通道详情页面
     * @param id 数据ID
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/view.action", method = {RequestMethod.GET})
    public String view(Long id, ModelMap modelMap) {
        if (Objects.nonNull(id)) {
            MarketChannel marketChannel = marketChannelService.get(id);
            marketChannel.setSecret(Base64.decodeStr(marketChannel.getSecret()));
            JSONObject jsonObject = JSONUtil.parseObj(marketChannel);
            modelMap.put("marketChannel", jsonObject);
        } else {
            modelMap.put("marketChannel", "{}");
        }
        return "marketChannel/view";
    }

    /**
     * 删除市场通道信息
     * @param id 数据ID
     * @return
     */
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @BusinessLogger(systemCode = MessageConstant.SYSTEM_CODE, businessType = "message_market_channel", notes = "删除市场通道信息", operationType = "del")
    public BaseOutput delete(Long id) {
        if (Objects.isNull(id)) {
            return BaseOutput.failure("删除失败！参数ID为空");
        }
        List<TriggersTemplate> list = triggersTemplateService.listByMarketChannelId(String.valueOf(id));
        if (CollectionUtils.isNotEmpty(list)) {
            return BaseOutput.failure("删除失败！该通道下还存在消息模板，不能删除！");
        }
        MarketChannel marketChannel = marketChannelService.get(id);
        if (Objects.nonNull(marketChannel)) {
            UserTicket userTicket = getUserTicket();
            LoggerUtil.buildBusinessLoggerContext(marketChannel.getId(), String.valueOf(marketChannel.getId()), userTicket.getId(), userTicket.getRealName(), userTicket.getFirmId(), buildLoggerContent(marketChannel, "").toString());
            marketChannelService.delete(id);
        }
        return BaseOutput.success("删除成功");
    }

    /**
     * 构建日志数据
     * @param marketChannel 市场通道
     * @return
     */
    private StringBuffer buildLoggerContent(MarketChannel marketChannel, String prefix) {
        StringBuffer stringBuffer = new StringBuffer(prefix);
        stringBuffer.append(" 市场: ").append(marketRpcService.getByCode(marketChannel.getMarketCode()).get().getName());
        stringBuffer.append(" 通道: ").append(MessageEnum.ChannelEnum.getInstance(marketChannel.getChannel()).getName());
        stringBuffer.append(" 签名: ").append(marketChannel.getSignature());
        stringBuffer.append(" 账号: ").append(marketChannel.getAccessKey());
        return stringBuffer;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    private UserTicket getUserTicket() {
        return SessionContext.getSessionContext().getUserTicket();
    }
}