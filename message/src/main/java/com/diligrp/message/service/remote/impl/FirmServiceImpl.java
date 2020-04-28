package com.diligrp.message.service.remote.impl;

import cn.hutool.core.collection.CollectionUtil;
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
import com.diligrp.message.service.remote.FirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/1 16:38
 */
@Service
public class FirmServiceImpl implements FirmService {

    @Autowired
    private DataAuthRpc dataAuthRpc;
    @Autowired
    private FirmRpc firmRpc;

    @Override
    public List<Firm> listByExample(FirmDto firm) {
        BaseOutput<List<Firm>> output = firmRpc.listByExample(firm);
        return output.isSuccess() ? output.getData() : null;
    }

    @Override
    public List<String> getCurrentUserFirmCodes() {
        return getCurrentUserFirmCodes(null);
    }

    @Override
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
    @Override
    public List<Firm> getCurrentUserFirms() {
        return getCurrentUserFirms(null);
    }

    /**
     * 获得当前用户拥有的所有Firm
     *
     * @param userId 用户ID，如果为空，则从session中获取，如果未获取到，返回空
     * @return
     */
    @Override
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
}
