package com.diligrp.message.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dili.commons.bstable.Tablepar;
import com.dili.ss.base.BaseServiceAdaptor;
import com.dili.ss.base.MyMapper;
import com.dili.ss.dto.IBaseDomain;
import com.diligrp.message.mapper.CustomMapper;
import com.diligrp.message.service.CustomBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/13 9:09
 */
@Service
public abstract class CustomBaseServiceImpl<T extends IBaseDomain, KEY extends Serializable> extends BaseServiceAdaptor<T, KEY> implements CustomBaseService<T, KEY> {

    @Autowired
    private CustomMapper<T> customMapper;


    @Autowired
    private MyMapper<T> mapper;

    /**
     * 如果不使用通用mapper，可以自行在子类覆盖getDao方法
     */
    @Override
    public MyMapper<T> getDao() {
        return this.mapper;
    }

    /**
     * 分页查询数据
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public PageInfo<T> pageInfo(Tablepar query) {
        if (query.getPageSize() >= 1) {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
        }
        if (StrUtil.isNotBlank(query.getSortName())) {
            query.setSortName(StrUtil.toUnderlineCase(query.getSortName()));
        } else {
            query.setSortName("id");
            query.setSortOrder("desc");
        }
        List<T> list = customMapper.selectByQuery(query);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
