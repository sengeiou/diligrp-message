package com.diligrp.message.controller;

import com.alibaba.fastjson.JSONObject;
import com.dili.commons.bstable.TableResult;
import com.dili.ss.metadata.ValueProviderUtils;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.domain.PushLog;
import com.diligrp.message.domain.query.PushLogQuery;
import com.diligrp.message.service.PushLogService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2020-10-10 16:41:27.
 */
@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/pushLog")
public class PushLogController {

    private String prefix = "pushLog";

    private final PushLogService pushLogService;

    /**
     * 跳转到PushLog页面
     * @param modelMap
     * @return String
     */
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return prefix+"/list";
    }

    /**
     * 分页查询PushLog
     * @param query
     * @throws Exception
     */
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public TableResult listPage(@RequestBody PushLogQuery query) {
        query.setMarketId(SessionContext.getSessionContext().getUserTicket().getFirmId());
        PageInfo<PushLog> page = pushLogService.pageInfo(query);
        TableResult<PushLog> result = new TableResult<>(page.getPageNum(), page.getTotal(), page.getList());
        try {
            List<Map> dataByProvider = ValueProviderUtils.buildDataByProvider(query.getMetadata(), result.getRows());
            return new TableResult(result.getPage(), result.getTotal(), dataByProvider);
        } catch (Exception e) {
            log.error(String.format("分页查询用户账号信息异常[]", e.getMessage()), e);
        }
        return new TableResult<>();
    }

    /**
     * 查看推送记录详情
     * @param id 记录id
     * @throws Exception
     */
    @RequestMapping(value = "/view.action", method = {RequestMethod.GET, RequestMethod.POST})
    public String view(Long id, ModelMap modelMap) {
        PushLog pushLog = pushLogService.get(id);
        modelMap.put("pushLogData", JSONObject.toJSONString(pushLog));
        return prefix + "/view";
    }
}