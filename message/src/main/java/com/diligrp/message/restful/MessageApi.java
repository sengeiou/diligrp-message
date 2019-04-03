package com.diligrp.message.restful;

import com.alibaba.fastjson.JSONObject;
import com.dili.ss.constant.ResultCode;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.component.MessageInfoHandler;
import com.diligrp.message.domain.vo.MessageInfoVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            messageInfoHandler.handler(messageInfoVo);
            return null;
        }catch (Exception e){
            return BaseOutput.failure("操作异常").setCode(ResultCode.APP_ERROR);
        }
    }

}
