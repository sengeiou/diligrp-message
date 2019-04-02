package com.diligrp.message.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.metadata.ValueProviderUtils;
import com.dili.ss.util.POJOUtils;
import com.dili.uap.sdk.domain.Firm;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.mapper.TriggersMapper;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.remote.FirmService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 * @author yuehongbo
 */
@Service
public class TriggersServiceImpl extends BaseServiceImpl<Triggers, Long> implements TriggersService {

    private TriggersMapper getActualDao() {
        return (TriggersMapper)getDao();
    }

    @Autowired
    private FirmService firmService;

    /**
     * 根据实体查询easyui分页结果， 支持用metadata信息中字段对应的provider构建数据
     *
     * @param triggers
     * @param useProvider 是否使用Provider
     * @return
     */
    @Override
    public EasyuiPageOutput listPageForEasyui(Triggers triggers, boolean useProvider) throws Exception {
        if (triggers.getRows() != null && triggers.getRows() >= 1) {
            PageHelper.startPage(triggers.getPage(), triggers.getRows());
        }
        if (StrUtil.isNotBlank(triggers.getSort())) {
            triggers.setSort(POJOUtils.humpToLineFast(triggers.getSort()));
        }
        TriggersVo query = new TriggersVo();
        BeanUtil.copyProperties(triggers,query);
        if (StrUtil.isBlank(query.getMarketCode())) {
            List<String> currentUserFirms = firmService.getCurrentUserFirmCodes();
            if(CollectionUtil.isEmpty(currentUserFirms)){
                return new EasyuiPageOutput(0, Collections.emptyList());
            }
            query.setMarketCodeList(currentUserFirms);
        }
        List<TriggersVo> triggerList = getActualDao().selectForPage(query);
        long total = triggerList instanceof Page ? ((Page) triggerList).getTotal() : (long) triggerList.size();
        List results = useProvider ? ValueProviderUtils.buildDataByProvider(triggers, triggerList) : triggerList;
        return new EasyuiPageOutput((int) total, results);
    }
}