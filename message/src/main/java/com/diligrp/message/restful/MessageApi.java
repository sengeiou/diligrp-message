package com.diligrp.message.restful;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.logger.sdk.annotation.BusinessLogger;
import com.dili.logger.sdk.util.LoggerUtil;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.uap.sdk.domain.Firm;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.component.MessageInfoHandler;
import com.diligrp.message.constants.MessageConstant;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.sdk.domain.input.MessageInfoInput;
import com.diligrp.message.sdk.domain.input.WhitelistCustomerInput;
import com.diligrp.message.service.WhitelistService;
import com.diligrp.message.service.remote.MarketRpcService;
import com.diligrp.message.utils.NetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * 短信发送
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 10:50
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/messageApi")
public class MessageApi {

    private final MessageInfoHandler messageInfoHandler;
    private final WhitelistService whitelistService;
    private final MarketRpcService marketRpcService;

    /**
     * 接收业务系统的短信发送
     * @param messageInfoInput 短信内容信息
     * @return
     */
    @RequestMapping(value = "/receiveMessage.api", method = {RequestMethod.GET, RequestMethod.POST})
    public BaseOutput receiveMessage(@RequestBody MessageInfoInput messageInfoInput, HttpServletRequest request) {
        try {
            if (Objects.isNull(messageInfoInput.getIp()) && Objects.nonNull(request)) {
                messageInfoInput.setIp(NetUtil.getIpAddress(request));
            }
            return messageInfoHandler.handler(messageInfoInput);
        } catch (Exception e) {
            log.error(String.format("短信发送数据[%s]处理异常:%s", JSONUtil.toJsonStr(messageInfoInput), e.getMessage()), e);
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR);
        }
    }

    /**
     * 接收业务系统的用户信息，并存入白名单信息
     * @param input 用户信息
     * @return
     */
    @RequestMapping(value = "/whitelistCustomer.api", method = {RequestMethod.GET, RequestMethod.POST})
    @BusinessLogger(systemCode = MessageConstant.SYSTEM_CODE, businessType = "message_whitelist", notes = "白名单数据来源其他系统同步", operationType = "add")
    public BaseOutput whitelistCustomer(@RequestBody @Validated WhitelistCustomerInput input, BindingResult br) {
        if (br.hasErrors()) {
            log.warn(String.format("白名单数据[%s]验证失败[%s]", JSONUtil.toJsonStr(input), br.getFieldError().getDefaultMessage()));
            return BaseOutput.failure().setMessage(br.getFieldError().getDefaultMessage());
        }
        try {
            Whitelist whitelist = new Whitelist();
            BeanUtil.copyProperties(input, whitelist, "id");
            whitelist.setSourceId(input.getSourceId());
            whitelist.setSource(String.valueOf(MessageEnum.WhitelistSourceEnum.SYSTEM.getCode()));
            whitelist.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
            whitelistService.saveWhitelist(whitelist);
            StringBuffer strb = new StringBuffer();
            strb.append(" 客户姓名：").append(whitelist.getCustomerName());
            strb.append(" 手机号：").append(whitelist.getCellphone());
            strb.append(" 有效期：").append(input.getStartDateTime().toLocalDate()).append(" - ").append(input.getEndDateTime().toLocalDate());
            Optional<Firm> byCode = marketRpcService.getByCode(input.getMarketCode());
            LoggerUtil.buildBusinessLoggerContext(whitelist.getId(), String.valueOf(whitelist.getId()), null, null, byCode.orElse(DTOUtils.newInstance(Firm.class)).getId(), strb.toString());
            return BaseOutput.success();
        } catch (Exception e) {
            log.error(String.format("白名单数据[%s]处理异常:%s", JSONUtil.toJsonStr(input), e.getMessage()), e);
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR);
        }
    }

    /**
     * 删除白名单用户
     * @param object 用户信息
     * @return
     */
    @Deprecated
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
            log.info(String.format("白名单数据【%s】逻辑删除成功", JSONUtil.toJsonStr(object)));
            return BaseOutput.success();
        } catch (Exception e) {
            log.error(String.format("白名单数据[%s]删除异常:%s",object.toJSONString() , e.getMessage()), e);
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR);
        }
    }

    /**
     * 删除白名单用户
     * @param marketCode 用户所属市场
     * @param id 用户ID
     * @return
     */
    @PostMapping(value = "/delWhitelistCustomer")
    public BaseOutput delWhitelistCustomer(@RequestParam("marketCode") String marketCode,@RequestParam("id") Long id) {
        try {
            Whitelist condition = new Whitelist();
            condition.setSourceId(id);
            condition.setMarketCode(marketCode);
            Whitelist domain = new Whitelist();
            domain.setDeleted(MessageEnum.DeletedEnum.YES.getCode());
            whitelistService.updateSelectiveByExample(domain, condition);
            log.info(String.format("白名单数据【%s】逻辑删除成功", JSONUtil.toJsonStr(condition)));
            return BaseOutput.success();
        } catch (Exception e) {
            log.error(String.format("白名单数据[%s,%d]删除异常:%s", marketCode, id, e.getMessage()), e);
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR);
        }
    }
}
