package com.diligrp.message.service.remote.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.constant.MessagePushConstant;
import com.diligrp.message.service.remote.IMessageService;
import com.diligrp.message.utils.EncryptUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <B>中国移动云MAS平台短信发送</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/10 18:03
 */
@Slf4j
@Component
public class ChinaMobileMasImpl implements IMessageService {

    @Value("${dili.message.base-url.china-mobile-mas:http://112.35.1.155:1992}")
    private String baseUrl;

    @Override
    public BaseOutput<String> sendSMS(JSONObject object) {
        try {
            HttpResponse response = HttpUtil.createPost(baseUrl + "/sms/norsubmit").body(getSmsParam(object)).execute();
            if (response.isOk()) {
                String responseBody = response.body();
                JSONObject jsonObject = JSONObject.parseObject(responseBody);
                BaseOutput output = new BaseOutput();
                Boolean success = jsonObject.getBoolean("success");
                if (success) {
                    output.setCode(ResultCode.OK);
                    output.setMessage(jsonObject.getString("msgGroup"));
                } else {
                    output.setCode(jsonObject.getString("rspcod"));
                    output.setMessage(ResponseCode.getResponseCode(output.getCode()).getDesc());
                    output.setMetadata("移动云MAS短信发送失败,返回 " + output.getMessage());
                }
                return output;
            } else {
                response.close();
                return BaseOutput.failure("调用移动云MAS返回错误").setCode(String.valueOf(response.getStatus())).setMetadata("移动云MAS调用失败,返回代码 " + response.getStatus());
            }
        } catch (Exception e) {
            log.error(String.format("移动云MAS通道发送异常 %s" + e.getMessage()), e);
            return BaseOutput.failure(e.getLocalizedMessage()).setMetadata("移动云MAS通道发送异常 >>>> " + e.toString());
        }
    }

    /**
     * 组装生成短信发送报文信息
     * @param object
     * @return
     */
    private String getSmsParam(JSONObject object) {
        Submit submit = new Submit();
        submit.setEcName(object.getString(MessagePushConstant.COMPANY_NAME));
        submit.setApId(object.getString(MessagePushConstant.ACCESS_KEY));
        submit.setSecretKey(object.getString(MessagePushConstant.SECRET));
        submit.setMobiles(object.getString(MessagePushConstant.PHONES));
        submit.setContent(object.getString(MessagePushConstant.CONTENT));
        submit.setSign(object.getString(MessagePushConstant.SIGN));
        submit.setAddSerial("");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(submit.getEcName());
        stringBuffer.append(submit.getApId());
        stringBuffer.append(submit.getSecretKey());
        stringBuffer.append(submit.getMobiles());
        stringBuffer.append(submit.getContent());
        stringBuffer.append(submit.getSign());
        stringBuffer.append(submit.getAddSerial());
        String selfMac = EncryptUtil.MD5HexString(stringBuffer.toString());
        submit.setMac(selfMac);
        String s = Base64.encodeBase64String(JSON.toJSONString(submit).getBytes());
        log.info(String.format("移动云MAS短信报文加密数据:%s", s));
        return s;
    }

    /**
     * 移动短信提交内容
     */
    @Getter
    @Setter
    class Submit{
        private String ecName;
        private String apId;
        private String secretKey;
        private String mobiles;
        private String content;
        private String sign;
        private String addSerial;
        private String mac;
    }

    /**
     * 移动返回的结果code涵义
     */
    enum ResponseCode{
        ILLEGAL_MAC("IllegalMac", "mac校验不通过。"),
        ILLEGAL_SIGNID("IllegalSignId", "无效的签名编码。"),
        INVALID_MESSAGE("InvalidMessage", "非法消息，请求数据解析失败。"),
        INVALID_USR_PWD("InvalidUsrOrPwd", "非法用户名/密码。"),
        NO_SIGNID("NoSignId", "未匹配到对应的签名信息。"),
        SUCCESS("success", "数据验证通过。"),
        TOO_MANY_MOBILES("TooManyMobiles", "手机号数量超限（>5000），应≤5000。"),
        ;
        @Getter
        private String code;
        @Getter
        private String desc;

        ResponseCode(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static ResponseCode getResponseCode(String code) {
            for (ResponseCode responseCode : ResponseCode.values()) {
                if (responseCode.getCode().equals(code)) {
                    return responseCode;
                }
            }
            return null;
        }

    }
}
