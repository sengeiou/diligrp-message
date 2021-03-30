package com.diligrp.message.component;

import com.diligrp.message.domain.Blacklist;
import com.diligrp.message.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2021/3/30 10:02
 */
@RequiredArgsConstructor
@Component
public class BlacklistExpiresTask {

    private final TaskScheduler taskScheduler;
    private final BlacklistService blacklistService;

    /**
     * 黑名单信息注册到任务中
     * @param blacklist
     */
    public void register(final Blacklist blacklist) {
        Runnable task = () -> {
            blacklistService.updateBlacklistStatus(blacklist);
        };
        taskScheduler.schedule(task, new Date());
    }
}
