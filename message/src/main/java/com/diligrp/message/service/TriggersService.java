package com.diligrp.message.service;

import com.dili.ss.base.BaseService;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.vo.TriggersVo;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 * @author yuehongbo
 */
public interface TriggersService extends BaseService<Triggers, Long> {

    /**
     * 根据实体查询easyui分页结果， 支持用metadata信息中字段对应的provider构建数据
     * @param triggers
     * @param useProvider 是否使用Provider
     * @throws Exception
     * @return
     */
    EasyuiPageOutput listPageForEasyui(Triggers triggers,boolean useProvider) throws Exception;

    /**
     * 根据条件聚合查询触发点及模板信息
     * @param triggers
     * @return
     */
    List<TriggersVo> selectForUnionTemplate(Triggers triggers);

    /**
     * 根据用户ID，操作启禁用
     * @param id ID
     * @param enable 是否启用(true-启用，false-禁用)
     * @return
     */
    BaseOutput updateEnable(Long id, Boolean enable);

    /**
     * 检查市场-系统-场景对应的关系是否不存在
     * @param marketCode
     * @param systemCode
     * @param sceneCode
     * @return true-不存在;false-存在
     */
    Boolean checkNotExist(String marketCode,String systemCode,String sceneCode);

    /**
     * 保存触发点信息
     * @param triggersVo
     * @return
     */
    BaseOutput saveInfo(TriggersVo triggersVo);
}