package com.diligrp.message.domain.input;

import com.diligrp.message.domain.Triggers;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2021/1/8 14:30
 */
@Getter
@Setter
public class TriggersSaveInput extends Triggers {

    /**
     * 触发点对应的模板信息
     */
    private List<TriggersTemplateSaveInput> templateList;
}
