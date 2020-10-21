package com.diligrp.message.service.remote;

import cn.hutool.core.collection.CollectionUtil;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.retrofitful.annotation.VOBody;
import com.dili.uap.sdk.domain.User;
import com.dili.uap.sdk.domain.UserPushInfo;
import com.dili.uap.sdk.domain.dto.UserPushInfoQuery;
import com.dili.uap.sdk.rpc.UserPushInfoRpc;
import com.dili.uap.sdk.rpc.UserRpc;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/19 16:30
 */
@RequiredArgsConstructor
@Service
public class UapUserRpcService {

    private final UserRpc userRpc;
    private final UserPushInfoRpc userPushInfoRpc;

    /**
     * 根据用户ID集批量获取用户信息
     * @param ids id集合
     * @return
     */
    public List<User> listUserByIds(Set<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<String> collect = ids.stream().map(t -> String.valueOf(t)).collect(Collectors.toList());
        BaseOutput<List<User>> baseOutput = userRpc.listUserByIds(collect);
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
}
