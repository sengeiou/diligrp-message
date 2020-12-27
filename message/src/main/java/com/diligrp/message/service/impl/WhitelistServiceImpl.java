package com.diligrp.message.service.impl;

import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.dto.IDTO;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.domain.vo.WhitelistVo;
import com.diligrp.message.mapper.WhitelistMapper;
import com.diligrp.message.service.WhitelistService;
import com.diligrp.message.service.remote.MarketRpcService;
import com.diligrp.message.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 * @author yuehongbo
 */
@RequiredArgsConstructor
@Service
public class WhitelistServiceImpl extends BaseServiceImpl<Whitelist, Long> implements WhitelistService {

    public WhitelistMapper getActualMapper() {
        return (WhitelistMapper)getDao();
    }

    private final MarketRpcService marketRpcService;

    @Override
    public EasyuiPageOutput findByWhitelistVo(WhitelistVo whitelistVo, boolean useProvider) throws Exception{
        whitelistVo.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
        if (whitelistVo.getKeywords() != null) {
            whitelistVo.setMetadata(IDTO.AND_CONDITION_EXPR, "(customer_name='" + whitelistVo.getKeywords() + "' or cellphone ='" + whitelistVo.getKeywords() + "')");
        }
        whitelistVo.setAuthMarkets(marketRpcService.getCurrentUserFirmCodes());
        return listEasyuiPageByExample(whitelistVo, useProvider);
    }

    @Override
    public boolean checkDate(Whitelist whitelist) {
        WhitelistVo vo = new WhitelistVo();
        vo.setDeleted(MessageEnum.DeletedEnum.NO.getCode());
        vo.setMarketCode(whitelist.getMarketCode());
        vo.setCellphone(whitelist.getCellphone());
        List<Whitelist> list = this.listByExample(vo);
        if (CollectionUtils.isEmpty(list)){
            return false;
        }

        for (int i = 0; i < list.size(); i++){
            if (whitelist.getEndDateTime().isBefore(list.get(i).getStartDateTime()) || whitelist.getStartDateTime().isAfter(list.get(i).getEndDateTime())){
                continue;
            }else {
                return true;
            }
        }
        return false;
        /**
         * 验证时间是否包含在已有时间区间
         * 传入的结束时间
         */
//        long count = list.stream().filter(t -> whitelist.getEndDateTime().isAfter(t.getStartDateTime())).count();
//        return count > 0;

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
        if (whitelist.getStartDateTime().isAfter(now)) {
            whitelist.setStatus(MessageEnum.WhitelistStatus.USELESS.getCode());
        } else if (whitelist.getEndDateTime().isBefore(now)) {
            whitelist.setStatus(MessageEnum.WhitelistStatus.EXPIRED.getCode());
        } else {
            whitelist.setStatus(MessageEnum.WhitelistStatus.ACTIVE.getCode());
        }
    }

}