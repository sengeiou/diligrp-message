package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.dto.IDTO;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.vo.WhitelistVo;
import com.diligrp.message.mapper.WhitelistMapper;
import com.diligrp.message.service.WhitelistService;
import com.diligrp.message.service.remote.FirmService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 */
@Service
public class WhitelistServiceImpl extends BaseServiceImpl<Whitelist, Long> implements WhitelistService {
    @Autowired
    FirmService firmService;

    public WhitelistMapper getActualDao() {
        return (WhitelistMapper)getDao();
    }

    @Override
    public EasyuiPageOutput findByWhitelistVo(WhitelistVo whitelistVo, boolean useProvider) throws Exception{
        whitelistVo.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
        if (whitelistVo.getKeywords() != null){
            whitelistVo.setMetadata(IDTO.AND_CONDITION_EXPR, "(customer_name="+whitelistVo.getKeywords()+" or cellphone ="+whitelistVo.getKeywords()+")");
        }
//        if (whitelistVo.getEndTime() != null){
//            Calendar c= Calendar.getInstance();
//            c.setTime(whitelistVo.getEndTime());
//            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
//            whitelistVo.setEndTime(c.getTime());//结束时间 的 23:59:59
//        }
        whitelistVo.setAuthMarkets(firmService.getCurrentUserFirmCodes());
        return listEasyuiPageByExample(whitelistVo, useProvider);
    }


    public boolean checkDate(Whitelist whitelist) {
        WhitelistVo vo = new WhitelistVo();
        vo.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
        vo.setAuthMarkets(firmService.getCurrentUserFirmCodes());
        vo.setCellphone(whitelist.getCellphone());
        vo.setMarketCode(whitelist.getMarketCode());
        List<Whitelist> list = this.listByExample(vo);
        if (CollectionUtils.isEmpty(list)){
            return false;
        }
        for (int i = 0; i < list.size(); i++){
            if (whitelist.getStartDate().before(list.get(i).getStartDate()) || whitelist.getEndDate().after(list.get(i).getEndDate())){
                return false;
            }
        }

        return true;
    }

}