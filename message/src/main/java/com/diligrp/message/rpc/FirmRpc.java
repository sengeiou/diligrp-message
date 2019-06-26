package com.diligrp.message.rpc;

import com.dili.ss.domain.BaseOutput;
import com.dili.ss.retrofitful.annotation.POST;
import com.dili.ss.retrofitful.annotation.Restful;
import com.dili.ss.retrofitful.annotation.VOBody;
import com.dili.ss.retrofitful.annotation.VOField;
import com.dili.uap.sdk.domain.Firm;
import com.dili.uap.sdk.domain.dto.FirmDto;

import java.util.List;

/**
 * <B>市场组织信息远程调用接口</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/1 16:48
 */
@Restful("${uap.contextPath}")
public interface FirmRpc {

    /**
     * 根据条件查询市场组织信息
     * @param firm
     * @return
     */
    @POST("/firmApi/listByExample.api")
    BaseOutput<List<Firm>> listByExample(@VOBody FirmDto firm) ;

    /**
     * 根据code获取单个市场信息
     * @param code code
     * @return
     */
    @POST("/firmApi/getByCode.api")
    BaseOutput<Firm> getByCode(@VOField("code") String code);
}