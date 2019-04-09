package com.diligrp.message.mapper;

import com.dili.ss.base.MyMapper;
import com.diligrp.message.domain.SequenceNumber;

import java.util.Map;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 16:42
 */
public interface SequenceNumberMapper extends MyMapper<SequenceNumber> {

    /**
     * 调用过程，生成编码
     * @param param
     */
    void getSequence(Map<String,Object> param);
}
