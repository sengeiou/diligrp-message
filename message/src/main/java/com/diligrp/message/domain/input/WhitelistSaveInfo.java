package com.diligrp.message.domain.input;

import com.diligrp.message.domain.Whitelist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/11/9 12:58
 */
@Getter
@Setter
public class WhitelistSaveInfo extends Whitelist {

    /**
     * 开始时间
     * 用于接收页面传入的对象
     */
    private LocalDate startDate;

    /**
     * 结束时间
     * 用于接收页面传入对象
     */
    private LocalDate endDate;
}
