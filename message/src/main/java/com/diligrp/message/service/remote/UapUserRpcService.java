package com.diligrp.message.service.remote;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.uap.sdk.domain.User;
import com.dili.uap.sdk.domain.UserPushInfo;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.domain.dto.UserPushInfoQuery;
import com.dili.uap.sdk.domain.dto.UserQuery;
import com.dili.uap.sdk.rpc.UserPushInfoRpc;
import com.dili.uap.sdk.rpc.UserRpc;
import com.dili.uap.sdk.session.SessionContext;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/19 16:30
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UapUserRpcService {

    private final UserRpc userRpc;
    private final UserPushInfoRpc userPushInfoRpc;


    /**
     * 根据条件查询用户信息
     * @param userQuery
     * @return
     */
    public List<User> listByExample(UserQuery userQuery) {
        BaseOutput<List<User>> baseOutput = userRpc.listByExample(userQuery);
        return baseOutput.isSuccess() ? baseOutput.getData() : Collections.emptyList();
    }

    /**
     * 根据用户ID集批量获取用户信息
     * @param ids id集合
     * @return
     */
    public List<User> listUserByIds(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        BaseOutput<List<User>> baseOutput = userRpc.listUserByIds(ids);
        return baseOutput.isSuccess() ? baseOutput.getData() : Collections.emptyList();
    }

    /**
     * 根据用户ID查询推送信息
     * @param ids 用户ID
     * @return 用户推送信息
     */
    public List<UserPushInfo> listPushInfoByExample(Set<Long> ids){
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        UserPushInfoQuery query = DTOUtils.newInstance(UserPushInfoQuery.class);
        List<Long> idList = Lists.newArrayList(ids);
        query.setUserIds(idList);
        BaseOutput<List<UserPushInfo>> baseOutput = userPushInfoRpc.listByExample(query);
        return baseOutput.isSuccess() ? baseOutput.getData() : Collections.emptyList();
    }

    /**
     * 根据真实姓名模糊获取当前市场的用户信息
     * @param realName 真实姓名
     * @return
     */
    public List<User> getCurrentMarketUser(String realName) {
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        if (null == userTicket) {
            return Collections.emptyList();
        }
        UserQuery condition = DTOUtils.newInstance(UserQuery.class);
        condition.setFirmCode(userTicket.getFirmCode());
        if (StrUtil.isNotBlank(realName)) {
            condition.setRealName(realName);
        }
        return listByExample(condition);
    }

    /**
     * 根据用户ID查询用户信息
     * @param id 用户ID
     * @return
     */
    public Optional<User> getById(Long id) {
        try {
            BaseOutput<User> userBaseOutput = userRpc.get(id);
            return Optional.ofNullable(userBaseOutput.getData());
        } catch (Exception e) {
            log.error(String.format("根据用户ID[%d]获取用户信息异常:%s", id, e.getMessage()), e);
            return Optional.empty();
        }
    }
}
