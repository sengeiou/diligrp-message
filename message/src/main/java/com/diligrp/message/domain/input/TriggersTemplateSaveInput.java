package com.diligrp.message.domain.input;

import com.diligrp.message.domain.TriggersTemplate;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2021/1/8 14:57
 */
@Getter
@Setter
public class TriggersTemplateSaveInput extends TriggersTemplate {

    /**
     * 页面传入的通道key的id数组
     */
    private long[] accessKeyIds;
}
