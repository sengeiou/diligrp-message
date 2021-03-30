package com.diligrp.message.scheduler;

import cn.hutool.core.collection.CollectionUtil;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.component.BlacklistExpiresTask;
import com.diligrp.message.domain.Blacklist;
import com.diligrp.message.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <B>黑名单有效期信息扫描</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/9/12 15:39
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class BlacklistExpiresScheduler {

    private final BlacklistService blacklistService;
    private final BlacklistExpiresTask blacklistExpiresTask;

    /**
     * 获取即将开始的黑名单数据，并放入任务调度器中
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void queryToStarted() {
        Example example = new Example(Blacklist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", MessageEnum.BlackWhitelistStatus.USELESS.getCode());
        criteria.andLessThanOrEqualTo("startDate", LocalDateTime.now());
        List<Blacklist> blacklists = blacklistService.selectByExample(example);
        updateBlacklistStatus(blacklists);
        log.info("扫描已经开始的黑名单数据，共扫描到 " + blacklists.size() + " 条数据");
    }

    /**
     * 获取已经到期的黑名单数据，并放入任务调度器中
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void queryToEnd() {
        Example example = new Example(Blacklist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", MessageEnum.BlackWhitelistStatus.ACTIVE.getCode());
        criteria.andLessThanOrEqualTo("endDate", LocalDateTime.now());
        List<Blacklist> blacklists = blacklistService.selectByExample(example);
        updateBlacklistStatus(blacklists);
        log.info("扫描已经结束的黑名单数据，共扫描到 " + blacklists.size() + " 条数据");
    }

    /**
     * 提交修改黑名单状态的数据到任务中
     * @param blacklists
     * @return
     */
    private boolean updateBlacklistStatus(List<Blacklist> blacklists) {
        if (CollectionUtil.isNotEmpty(blacklists)) {
            blacklists.stream().forEach(r -> {
                blacklistExpiresTask.register(r);
            });
        }
        return true;
    }
}
