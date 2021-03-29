package com.diligrp.message.scheduler;

import cn.hutool.core.collection.CollectionUtil;
import com.diligrp.message.common.enums.MessageEnum;
import com.diligrp.message.component.WhitelistExpiresTask;
import com.diligrp.message.domain.Whitelist;
import com.diligrp.message.service.WhitelistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <B>白名单有效期信息扫描</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/9/12 15:39
 */
@Slf4j
@Component
public class WhitelistExpiresScheduler {

    @Autowired
    private WhitelistService whitelistService;
    @Autowired
    private WhitelistExpiresTask whitelistExpiresTask;

    /**
     * 获取即将开始的白名单数据，并放入任务调度器中
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void queryToStarted() {
        Example example = new Example(Whitelist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", MessageEnum.BlackWhitelistStatus.USELESS.getCode());
        criteria.andEqualTo("deleted", MessageEnum.DeletedEnum.NO.getCode());
        criteria.andLessThanOrEqualTo("startDate", LocalDateTime.now());
        List<Whitelist> whitelists = whitelistService.selectByExample(example);
        updateWhitelistStatus(whitelists);
        log.info("扫描已经开始的白名单数据，共扫描到 " + whitelists.size() + " 条数据");
    }

    /**
     * 获取已经到期的白名单数据，并放入任务调度器中
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void queryToEnd() {
        Example example = new Example(Whitelist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", MessageEnum.BlackWhitelistStatus.ACTIVE.getCode());
        criteria.andEqualTo("deleted", MessageEnum.DeletedEnum.NO.getCode());
        criteria.andLessThanOrEqualTo("endDate", LocalDateTime.now());
        List<Whitelist> whitelists = whitelistService.selectByExample(example);
        updateWhitelistStatus(whitelists);
        log.info("扫描已经结束的白名单数据，共扫描到 " + whitelists.size() + " 条数据");
    }

    /**
     * 提交修改白名单状态的数据到任务中
     * @param whitelists
     * @return
     */
    private boolean updateWhitelistStatus(List<Whitelist> whitelists) {
        if (CollectionUtil.isNotEmpty(whitelists)) {
            whitelists.stream().forEach(r -> {
                whitelistExpiresTask.register(r);
            });
        }
        return true;
    }
}
