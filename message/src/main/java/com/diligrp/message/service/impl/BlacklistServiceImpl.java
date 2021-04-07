package com.diligrp.message.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.dto.IDTO;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.domain.Blacklist;
import com.diligrp.message.domain.query.BlacklistQuery;
import com.diligrp.message.mapper.BlacklistMapper;
import com.diligrp.message.service.BlacklistService;
import com.diligrp.message.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:54:30.
 * @author yuehongbo
 */
@RequiredArgsConstructor
@Service
public class BlacklistServiceImpl extends BaseServiceImpl<Blacklist, Long> implements BlacklistService {

    public BlacklistMapper getActualMapper() {
        return (BlacklistMapper)getDao();
    }

    @Override
    public EasyuiPageOutput listPageForEasyui(BlacklistQuery blacklistQuery, boolean useProvider) throws Exception{
        if (StrUtil.isNotBlank(blacklistQuery.getKeywords())) {
            blacklistQuery.setMetadata(IDTO.AND_CONDITION_EXPR, "(customer_name='" + blacklistQuery.getKeywords() + "' or cellphone ='" + blacklistQuery.getKeywords() + "')");
        }
        return listEasyuiPageByExample(blacklistQuery, useProvider);
    }

    @Override
    public boolean checkDate(Blacklist blacklist) {
        BlacklistQuery query = new BlacklistQuery();
        query.setMarketCode(blacklist.getMarketCode());
        query.setCellphone(blacklist.getCellphone());
        List<Blacklist> list = this.listByExample(query);
        if (CollectionUtils.isEmpty(list)){
            return false;
        }
        /**
         * 验证时间是否包含在已有时间区间
         * 传入的结束时间
         */
        long count = list.stream().filter(t -> blacklist.getEndTime().isAfter(t.getStartTime())).count();
        return count > 0;

    }

    @Override
    public Set<String> queryValidByMarketCode(String marketCode) {
        return getActualMapper().queryValidByMarketCode(marketCode);
    }

    @Override
    public Integer updateBlacklistStatus(Blacklist blacklist) {
        if (Objects.isNull(blacklist) || Objects.isNull(blacklist.getId())) {
            return 0;
        }
        produceStatus(blacklist);
        Blacklist update = new Blacklist();
        update.setId(blacklist.getId());
        update.setStatus(blacklist.getStatus());
        update.setModified(MessageUtil.now());
        return updateSelective(update);
    }

    @Override
    public Integer saveBlacklist(Blacklist blacklist) {
        if (Objects.isNull(blacklist)) {
            return 0;
        }
        produceStatus(blacklist);
        return saveOrUpdateSelective(blacklist);
    }

    /**
     * 获取白名单状态值
     * @param blacklist
     */
    private void produceStatus(Blacklist blacklist) {
        LocalDateTime now = LocalDateTime.now();
        if (blacklist.getStartTime().isAfter(now)) {
            blacklist.setStatus(MessageEnum.BlackWhitelistStatus.USELESS.getCode());
        } else if (blacklist.getEndTime().isBefore(now)) {
            blacklist.setStatus(MessageEnum.BlackWhitelistStatus.EXPIRED.getCode());
        } else {
            blacklist.setStatus(MessageEnum.BlackWhitelistStatus.ACTIVE.getCode());
        }
    }

}