package com.diligrp.message.service.remote.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.constant.MessagePushConstant;
import com.diligrp.message.service.remote.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <B>网建系统短信发送</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/11 14:33
 */
@Slf4j
@Component
public class SmsChineseImpl implements IMessageService {

    private String baseUrl= "http://utf8.api.smschinese.cn";

    @Override
    public BaseOutput<String> sendSMS(JSONObject object) {
        JSONObject params = new JSONObject();
        params.put("Uid", object.getString(MessagePushConstant.ACCESS_KEY));
        params.put("Key", object.getString(MessagePushConstant.SECRET));
        params.put("smsMob", object.getString(MessagePushConstant.PHONES));
        params.put("smsText", object.getString(MessagePushConstant.CONTENT));
        try {
            HttpResponse response = HttpUtil.createPost(baseUrl).header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded").header("charset", "utf8").form(params).execute();
            if (response.isOk()) {
                Integer responseCode = Integer.valueOf(response.body());
                BaseOutput output = new BaseOutput();
                if (responseCode > 0) {
                    output.setCode(ResultCode.OK);
                } else {
                    output.setCode(String.valueOf(responseCode));
                    output.setMessage(ResponseCode.getResponseCode(responseCode).getDesc());
                }
                return output;
            } else {
                response.close();
                return BaseOutput.failure("调用网建返回错误").setCode(String.valueOf(response.getStatus()));
            }
        } catch (Exception e) {
            log.error("网建通道发送异常," + e.getMessage(), e);
            return BaseOutput.failure(e.getLocalizedMessage()).setMetadata("网建短信通道异常 >>>> " + e);
        }
    }

    /**
     * 网建接口返回的错误码对照
     */
    enum ResponseCode{
        ACCESS_KEY_ERROR(-1, "没有该用户账户"),
        SECRET_ERROR(-2, "接口密钥不正确"),
        MD5_SECRET_ERROR(-21, "MD5接口密钥加密不正确"),
        BALANCE_ERROR(-3, "短信数量不足"),
        ACCESS_DISABLE(-11, "该用户被禁用"),
        ILLEGAL(-14, "短信内容出现非法字符"),
        CELLPHONE(-4, "手机号格式不正确"),
        CELLPHONE_NON(-41, "手机号码为空"),
        CONTENT_NON(-42, "短信内容为空"),
        SIGN_ERROR(-51, "短信签名格式不正确,接口签名格式为：【签名内容】"),
        SIGN_TOO_LONG(-52, "短信签名太长,建议签名10个字符以内"),
        IP(-6, "IP限制"),
        ;
        private Integer code;
        private String desc;

        ResponseCode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static ResponseCode getResponseCode(Integer code) {
            for (ResponseCode responseCode : ResponseCode.values()) {
                if (responseCode.getCode().equals(code)) {
                    return responseCode;
                }
            }
            return null;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
