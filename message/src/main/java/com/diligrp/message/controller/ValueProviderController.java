package com.diligrp.message.controller;

import com.dili.ss.domain.BaseOutput;
import com.dili.ss.metadata.ValueProviderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/29 16:15
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/valueProvider")
public class ValueProviderController {

    private final ValueProviderUtils valueProviderUtils;

    /**
     * 获取值提供者的显示信息
     * @param queryMap
     * @return
     */
    @PostMapping({"/getDisplayText.action"})
    public BaseOutput<String> getDisplayText(@RequestBody Map<String, Object> queryMap) {
        String provider = queryMap.get("provider").toString();
        queryMap.remove("provider");
        String value = this.valueProviderUtils.getDisplayText(provider, queryMap.get("value"), queryMap);
        return BaseOutput.successData(value);
    }
}
