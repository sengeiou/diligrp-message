package com.diligrp.message.domain.query;

import com.diligrp.message.domain.Blacklist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2021/3/26 14:36
 */
@Getter
@Setter
public class BlacklistQuery extends Blacklist {

    /**
     * 关键字查询
     */
    @Transient
    private String keywords;
}
