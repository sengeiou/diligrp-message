package com.diligrp.message.service.remote;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.uap.sdk.domain.Firm;
import com.dili.uap.sdk.domain.UserDataAuth;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.domain.dto.FirmDto;
import com.dili.uap.sdk.glossary.DataAuthType;
import com.dili.uap.sdk.rpc.DataAuthRpc;
import com.dili.uap.sdk.rpc.FirmRpc;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.constants.MessageConstant;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/1 16:38
 */
@RequiredArgsConstructor
@Service
public class MarketRpcService {

    private final DataAuthRpc dataAuthRpc;
    private final FirmRpc firmRpc;

    @Resource(name = "caffeineTimedCache")
    private Cache<String, String> caffeineTimedCache;

    /**
     * 根据条件查询市场
     * @param firm
     * @author wangmi
     * @return
     */
    public List<Firm> listByExample(FirmDto firm) {
        BaseOutput<List<Firm>> output = firmRpc.listByExample(firm);
        return output.isSuccess() ? output.getData() : null;
    }

    /**
     * 当前用户拥有访问权限的firmCode
     * @return
     */
    public List<String> getCurrentUserFirmCodes() {
        return getCurrentUserFirmCodes(null);
    }

    /**
     * 当前用户拥有访问权限的firmCode集
     * @param userId 用户ID
     * @return
     */
    public List<String> getCurrentUserFirmCodes(Long userId) {
        List<Firm> list = this.getCurrentUserFirms(userId);
        List<String> resultList = list.stream().map(Firm::getCode).collect(Collectors.toList());
        return resultList;
    }

    /**
     * 获得当前用户拥有的所有Firm
     *
     * @return
     */
    public List<Firm> getCurrentUserFirms() {
        return getCurrentUserFirms(null);
    }

    /**
     * 获得当前用户拥有的所有Firm
     *
     * @param userId 用户ID，如果为空，则从session中获取，如果未获取到，返回空
     * @return
     */
    public List<Firm> getCurrentUserFirms(Long userId) {
        UserDataAuth userDataAuth = DTOUtils.newInstance(UserDataAuth.class);
        if (null == userId) {
            UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
            if (null == userTicket) {
                return Collections.emptyList();
            }
            userDataAuth.setUserId(SessionContext.getSessionContext().getUserTicket().getId());
        } else {
            userDataAuth.setUserId(userId);
        }
        userDataAuth.setRefCode(DataAuthType.MARKET.getCode());
        BaseOutput<List<Map>> out = dataAuthRpc.listUserDataAuthDetail(userDataAuth);
        if (out.isSuccess() && CollectionUtil.isNotEmpty(out.getData())) {
            List<String> firmCodeList = (List<String>) out.getData().stream().flatMap(m -> m.keySet().stream()).collect(Collectors.toList());
            FirmDto firmDto = DTOUtils.newInstance(FirmDto.class);
            firmDto.setCodes(firmCodeList);
            BaseOutput<List<Firm>> listBaseOutput = firmRpc.listByExample(firmDto);
            if (listBaseOutput.isSuccess() && CollectionUtil.isNotEmpty(listBaseOutput.getData())) {
                return listBaseOutput.getData();
            } else {
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 通过code查询firm
     * @param code firmCode
     * @return
     */
    public Optional<Firm> getByCode(String code) {
        String cacheKey = MessageConstant.CACHE_KEY + "_marketCode_" + code;
        String str = caffeineTimedCache.get(cacheKey, t -> {
            BaseOutput<Firm> byCode = firmRpc.getByCode(code);
            if (byCode.isSuccess() && Objects.nonNull(byCode.getData())) {
                return JSONUtil.toJsonStr(byCode.getData());
            }
            return null;
        });
        if (StrUtil.isNotBlank(str)) {
            Firm dto = JSONUtil.toBean(str, Firm.class);
            return Optional.ofNullable(dto);
        }
        return Optional.empty();
    }

    /**
     * 通过id查询firm
     * @param id firmId
     * @return
     */
    public Optional<Firm> getById(Long id) {
        String cacheKey = MessageConstant.CACHE_KEY + "_marketId_" + id;
        String str = caffeineTimedCache.get(cacheKey, t -> {
            BaseOutput<Firm> byId = firmRpc.getById(id);
            if (byId.isSuccess() && Objects.nonNull(byId.getData())) {
                return JSONUtil.toJsonStr(byId.getData());
            }
            return null;
        });
        if (StrUtil.isNotBlank(str)) {
            Firm dto = JSONUtil.toBean(str, Firm.class);
            return Optional.ofNullable(dto);
        }
        return Optional.empty();
    }
}
