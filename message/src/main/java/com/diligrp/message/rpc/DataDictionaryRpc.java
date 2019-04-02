package com.diligrp.message.rpc;

import com.dili.ss.domain.BaseOutput;
import com.dili.ss.retrofitful.annotation.POST;
import com.dili.ss.retrofitful.annotation.Restful;
import com.dili.ss.retrofitful.annotation.VOBody;
import com.dili.uap.sdk.domain.DataDictionaryValue;

import java.util.List;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/2 15:58
 */
@Restful("${uap.contextPath}")
public interface DataDictionaryRpc {

    @POST("/dataDictionaryApi/list.api")
    BaseOutput<List<DataDictionaryValue>> list(@VOBody DataDictionaryValue dataDictionaryValue);

}