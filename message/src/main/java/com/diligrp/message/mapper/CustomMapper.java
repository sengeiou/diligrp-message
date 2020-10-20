package com.diligrp.message.mapper;

import com.dili.commons.bstable.Tablepar;

import java.util.List;

/**
 * 自定义mapper
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/17 17:03
 */
public interface CustomMapper<T> {

    /**
     * 分页查询数据信息
     * @param query 查询条件
     * @return
     */
    List<T> selectByQuery(Tablepar query);
}
