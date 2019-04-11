package com.diligrp.message.service.remote.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.constant.MessagePushConstant;
import com.diligrp.message.service.remote.IMessageService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <B>网建系统短信发送</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/11 14:33
 */
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
        HttpResponse response = HttpUtil.createPost(baseUrl).header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded").header("charset", "utf8").form(params).execute();
        if (response.isOk()) {
            String responseBody = response.body();
            BaseOutput output = new BaseOutput();
            if (Integer.valueOf(responseBody) > 0) {
                output.setCode(ResultCode.OK);
            } else {
                output.setCode(responseBody);
                output.setResult("网建短信发送返回错误，参考错误码：" + responseBody);
            }
            return output;
        } else {
            response.close();
            return BaseOutput.failure("调用网建返回错误").setCode(String.valueOf(response.getStatus()));
        }
    }
}
