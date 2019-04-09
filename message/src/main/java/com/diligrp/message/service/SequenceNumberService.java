package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.diligrp.message.common.enums.BizNumberTypeEnum;
import com.diligrp.message.domain.SequenceNumber;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 16:45
 */
public interface SequenceNumberService extends BaseService<SequenceNumber, Long> {

    /**
     * 根据业务类型获取编号
     * @param bizNumberType 业务类型，参照BizNumberType枚举
     * @return
     */
    String getBizNumberByType(BizNumberTypeEnum bizNumberType);

    /**
     * 根据业务类型获取编号
     * @param bizNumberType 业务类型，参照BizNumberType枚举
     * @param prefix 自定义前缀
     * @param addFirst 是否添加到默认前缀的前边(true-是-追加到前边;false-否-追加到后边)
     * @return
     */
    String getBizNumberByType(BizNumberTypeEnum bizNumberType, String prefix, Boolean addFirst);

    /**
     * 根据业务类型获取编号
     * @param type 业务类型，自定义
     * @param dbFormat 数据库格式化方法
     * @param length 编码长度
     * @param prefix 自定义前缀
     * @return
     */
    String getBizNumberByType(String type,String dbFormat,Integer length,String prefix);
}
