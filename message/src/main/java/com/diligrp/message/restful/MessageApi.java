package com.diligrp.message.restful;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.component.MessageInfoHandler;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.vo.MessageInfoVo;
import com.diligrp.message.service.WhitelistService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 10:50
 */
@RestController
@RequestMapping("/messageApi")
public class MessageApi {

    @Autowired
    private MessageInfoHandler messageInfoHandler;
    @Autowired
    private WhitelistService whitelistService;

    /**
     * 接收业务系统的短信发送
     * @param messageInfoVo 短信内容信息
     * @return
     */
    @RequestMapping(value = "/receiveMessage.api", method = {RequestMethod.GET, RequestMethod.POST})
    public BaseOutput batchUpdateNewFlag(@RequestBody @Validated MessageInfoVo messageInfoVo, BindingResult br) {
        if (br.hasErrors()) {
            return BaseOutput.failure().setResult(br.getFieldError().getDefaultMessage());
        }
        try {
            return messageInfoHandler.handler(messageInfoVo);
        }catch (Exception e){
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR);
        }
    }


    /**
     * 接收业务系统的用户信息，并存入白名单信息
     * @param object 用户信息
     * @return
     */
    @RequestMapping(value = "/whitelistCustomer.api", method = {RequestMethod.GET, RequestMethod.POST})
    public BaseOutput whitelistCustomer(@RequestBody JSONObject object) {
        try {

            String customerName = object.getString("customerName");
            String cellphone = object.getString("cellphone");
            Date startDate = object.getDate("startDate");
            Date endDate = object.getDate("endDate");
            String marketCode = object.getString("marketCode");
            Long sourceId = object.getLong("id");
            if (StrUtil.isBlank(customerName) || StrUtil.isBlank(cellphone) || null == startDate || null == endDate || StrUtil.isBlank(marketCode) || null == sourceId) {
                return BaseOutput.failure("参数错误");
            }
            Whitelist whitelist = new Whitelist();
            whitelist.setCustomerName(customerName);
            whitelist.setCellphone(cellphone);
            whitelist.setStartDate(startDate);
            whitelist.setEndDate(endDate);
            whitelist.setMarketCode(marketCode);
            whitelist.setSourceId(sourceId);
            whitelist.setSource(String.valueOf(MessageEnum.MessageSourceEnum.SYSTEM.getCode()));
            whitelist.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
            whitelistService.insertSelective(whitelist);
            return BaseOutput.success();
        }catch (Exception e){
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR);
        }
    }

    /**
     * 删除白名单用户
     * @param object 用户信息
     * @return
     */
    @RequestMapping(value = "/delWhitelistCustomer.api", method = {RequestMethod.GET, RequestMethod.POST})
    public BaseOutput delWhitelistCustomer(@RequestBody JSONObject object) {
        try {
            String marketCode = object.getString("marketCode");
            Long sourceId = object.getLong("id");
            if (StrUtil.isBlank(marketCode) || null == sourceId) {
                return BaseOutput.failure("参数错误");
            }
            Whitelist condition = new Whitelist();
            condition.setSourceId(sourceId);
            condition.setMarketCode(marketCode);
            Whitelist domain = new Whitelist();
            domain.setDeleted(MessageEnum.DeletedEnum.YES.getCode());
            whitelistService.updateSelectiveByExample(domain, condition);
            return BaseOutput.success();
        } catch (Exception e) {
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR);
        }
    }
}
