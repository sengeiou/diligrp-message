package com.diligrp.message.service;

import com.dili.commons.bstable.Tablepar;
import com.dili.ss.base.BaseService;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/13 15:25
 */
public interface CustomBaseService<T, KEY extends Serializable> extends BaseService<T, KEY> {

    /**
     * 分页查询数据
     * @param query 查询条件
     * @return
     */
    PageInfo<T> pageInfo(Tablepar query);
}
