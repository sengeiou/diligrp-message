package com.diligrp.message.service.remote.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.service.remote.IMessageService;
import org.springframework.stereotype.Component;

/**
 * <B>阿里大于通道实现</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/9 14:42
 */
@Component
public class AlidayuSmsImpl implements IMessageService {

    @Override
    public BaseOutput<String> sendSMS(JSONObject object) {
        DefaultProfile profile = DefaultProfile.getProfile("default", object.getString("accessKey"),object.getString("secret"));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = buildData(object.getString("sign"), object.getString("phones"), object.getString("templateCode"),object.getString("parameters"));
        try {
            CommonResponse response = client.getCommonResponse(request);
            if (null != response) {
                JSONObject data = JSONObject.parseObject(response.getData());
                String code = data.getString("Code");
                BaseOutput output = new BaseOutput();
                //请求 ID。无论调用接口成功与否，都会返回请求 ID。
                output.setData(data.getString("RequestId"));
                if ("OK".equals(code.toUpperCase())) {
                    output.setCode(ResultCode.OK);
                    //发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态
                    output.setResult(data.getString("BizId"));
                } else {
                    output.setCode(code);
                    output.setResult(data.getString("Message"));
                }
                return output;
            }
        } catch (ServerException e) {
        } catch (ClientException e) {
        }
        return BaseOutput.failure();
    }

    /**
     * 构建发送数据
     * @param signName
     * @param phone
     * @param templateCode
     * @param params
     * @return
     */
    private CommonRequest buildData(String signName, String phone, String templateCode, String params) {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setProduct("Dysmsapi");
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", params);
        return request;
    }
}
