package com.diligrp.message.component;

import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.service.WhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <B>白名单有效期信息任务</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/9/12 18:33
 */
@Component
public class WhitelistExpiresTask {

    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private WhitelistService whitelistService;

    /**
     * 白名单信息注册到任务中
     * @param whitelist
     */
    public void register(final Whitelist whitelist) {
        Runnable task = () -> {
            whitelistService.updateWhitelistStatus(whitelist);
        };
        taskScheduler.schedule(task, new Date());
    }
}
