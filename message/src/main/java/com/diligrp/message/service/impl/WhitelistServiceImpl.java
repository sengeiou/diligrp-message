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
import com.diligrp.message.utils.MessageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 * @author yuehongbo
 */
@Service
public class WhitelistServiceImpl extends BaseServiceImpl<Whitelist, Long> implements WhitelistService {
    @Autowired
    FirmService firmService;

    public WhitelistMapper getActualMapper() {
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

    @Override
    public boolean checkDate(Whitelist whitelist) {
        WhitelistVo vo = new WhitelistVo();
        vo.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
        vo.setMarketCode(whitelist.getMarketCode());
        vo.setCellphone(whitelist.getCellphone());
        vo.setMarketCode(whitelist.getMarketCode());
        List<Whitelist> list = this.listByExample(vo);
        if (CollectionUtils.isEmpty(list)){
            return false;
        }

        for (int i = 0; i < list.size(); i++){
            if (whitelist.getEndDate().isBefore(list.get(i).getStartDate()) || whitelist.getStartDate().isAfter(list.get(i).getEndDate())){
                continue;
            }else {
                return true;
            }
        }
        /**
         * 验证时间是否包含在已有时间区间
         * 传入的结束时间
         */
//        long count = list.stream().filter(t -> whitelist.getEndDate().isAfter(t.getStartDate())).count();
//        return count > 0;
        return false;
    }

    @Override
    public Set<String> queryValidByMarketCode(Whitelist whitelist) {
        return getActualMapper().queryValidByMarketCode(whitelist);
    }

    @Override
    public Integer updateWhitelistStatus(Whitelist whitelist) {
        if (null == whitelist || null == whitelist.getId()) {
            return 0;
        }
        produceStatus(whitelist);
        Whitelist update = new Whitelist();
        update.setId(whitelist.getId());
        update.setStatus(whitelist.getStatus());
        update.setModified(MessageUtil.now());
        return updateSelective(update);
    }

    @Override
    public Integer saveWhitelist(Whitelist whitelist) {
        if (null == whitelist) {
            return 0;
        }
        produceStatus(whitelist);
        return saveOrUpdateSelective(whitelist);
    }

    /**
     * 获取白名单状态值
     * @param whitelist
     */
    private void produceStatus(Whitelist whitelist) {
        LocalDateTime now = LocalDateTime.now();
        if (whitelist.getStartDate().isAfter(now)) {
            whitelist.setStatus(MessageEnum.WhitelistStatus.USELESS.getCode());
        } else if (whitelist.getEndDate().isBefore(now)) {
            whitelist.setStatus(MessageEnum.WhitelistStatus.EXPIRED.getCode());
        } else {
            whitelist.setStatus(MessageEnum.WhitelistStatus.ACTIVE.getCode());
        }
    }

}