package com.diligrp.message.service.remote;

import cn.hutool.core.util.StrUtil;
import com.dili.ss.domain.BaseOutput;
import com.diligrp.message.common.enums.BizNumberTypeEnum;
import com.diligrp.message.rpc.UidRpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/11 16:38
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UidRpcService {

    private final UidRpc uidRpc;

    /**
     * 获取业务编号
     * @param bizNumberType 业务类型
     * @return 业务编号
     */
    public Optional<String> getBizNumber(BizNumberTypeEnum bizNumberType) {
        return getBizNumber(bizNumberType,false);
    }

    /**
     * 获取业务编号
     * @param bizNumberType 业务类型
     * @param isNow 如果uid获取失败，是否默认返回当前时间戳
     * @return 业务编号
     */
    public Optional<String> getBizNumber(BizNumberTypeEnum bizNumberType, Boolean isNow) {
        try {
            BaseOutput<String> bizNumberOutput = uidRpc.bizNumber(bizNumberType.getType());
            if (StrUtil.isNotBlank(bizNumberOutput.getData())) {
                return Optional.of(bizNumberOutput.getData());
            }

        } catch (Exception e) {
            log.error(String.format("根据业务类型[%s]获取编号异常:%s", bizNumberType.getName(), e.getMessage()), e);
        }
        if (isNow) {
            return Optional.of(String.valueOf(System.currentTimeMillis()));
        }
        return Optional.empty();
    }
}
