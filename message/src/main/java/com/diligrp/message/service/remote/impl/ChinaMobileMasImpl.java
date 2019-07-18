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
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * <B>中国移动云MAS平台短信发送</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/10 18:03
 */
@Component
public class ChinaMobileMasImpl implements IMessageService {

    private String baseUrl = "http://112.35.1.155:1992/sms";

    @Override
    public BaseOutput<String> sendSMS(JSONObject object) {
        HttpResponse response = HttpUtil.createPost(baseUrl + "/norsubmit").body(getSmsParam(object)).execute();
        if (response.isOk()){
            String responseBody = response.body();
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            BaseOutput output = new BaseOutput();
            Boolean success = jsonObject.getBoolean("success");
            if (success){
                output.setCode(ResultCode.OK);
                output.setMessage(jsonObject.getString("msgGroup"));
            }else{
                output.setCode(jsonObject.getString("rspcod"));
                output.setMessage(ResponseCode.getResponseCode(output.getCode()).getDesc());
            }
            return output;
        }else{
            response.close();
            return BaseOutput.failure("调用移动云MAS返回错误").setCode(String.valueOf(response.getStatus()));
        }
    }

    private String getSmsParam(JSONObject object){
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
        return Base64.encodeBase64String(JSON.toJSONString(submit).getBytes());
    }

    /**
     * 移动短信提交内容
     */
    class Submit{
        private String ecName;
        private String apId;
        private String secretKey;
        private String mobiles;
        private String content;
        private String sign;
        private String addSerial;
        private String mac;

        public String getEcName() {
            return ecName;
        }
        public void setEcName(String ecName) {
            this.ecName = ecName;
        }
        public String getApId() {
            return apId;
        }
        public void setApId(String apId) {
            this.apId = apId;
        }
        public String getSecretKey() {
            return secretKey;
        }
        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
        public String getMobiles() {
            return mobiles;
        }
        public void setMobiles(String mobiles) {
            this.mobiles = mobiles;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public String getSign() {
            return sign;
        }
        public void setSign(String sign) {
            this.sign = sign;
        }
        public String getAddSerial() {
            return addSerial;
        }
        public void setAddSerial(String addSerial) {
            this.addSerial = addSerial;
        }
        public String getMac() {
            return mac;
        }
        public void setMac(String mac) {
            this.mac = mac;
        }
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
        private String code;
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

        public String getCode() {
            return code;
        }
        public String getDesc() {
            return desc;
        }
    }
}
