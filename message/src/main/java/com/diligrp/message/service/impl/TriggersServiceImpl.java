package com.diligrp.message.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.metadata.ValueProviderUtils;
import com.dili.ss.util.POJOUtils;
import com.diligrp.message.common.enums.BizNumberTypeEnum;
import com.diligrp.message.common.enums.TriggersEnum;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.mapper.TriggersMapper;
import com.diligrp.message.service.SequenceNumberService;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.TriggersTemplateService;
import com.diligrp.message.service.remote.FirmService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 * @author yuehongbo
 */
@Service
public class TriggersServiceImpl extends BaseServiceImpl<Triggers, Long> implements TriggersService {

    private TriggersMapper getActualMapper() {
        return (TriggersMapper)getDao();
    }

    @Autowired
    private FirmService firmService;
    @Autowired
    private TriggersTemplateService triggersTemplateService;
    @Autowired
    private SequenceNumberService sequenceNumberService;

    /**
     * 根据实体查询easyui分页结果， 支持用metadata信息中字段对应的provider构建数据
     *
     * @param triggers
     * @param useProvider 是否使用Provider
     * @return
     */
    @Override
    public EasyuiPageOutput listPageForEasyui(Triggers triggers, boolean useProvider) throws Exception {
        if (triggers.getRows() != null && triggers.getRows() >= 1) {
            PageHelper.startPage(triggers.getPage(), triggers.getRows());
        }
        if (StrUtil.isNotBlank(triggers.getSort())) {
            triggers.setSort(POJOUtils.humpToLineFast(triggers.getSort()));
        }
        TriggersVo query = new TriggersVo();
        BeanUtil.copyProperties(triggers,query);
        if (StrUtil.isBlank(query.getMarketCode())) {
            List<String> currentUserFirms = firmService.getCurrentUserFirmCodes();
            if(CollectionUtil.isEmpty(currentUserFirms)){
                return new EasyuiPageOutput(0, Collections.emptyList());
            }
            query.setMarketCodeList(currentUserFirms);
        }
        List<TriggersVo> triggerList = getActualMapper().selectForPage(query);
        long total = triggerList instanceof Page ? ((Page) triggerList).getTotal() : (long) triggerList.size();
        List results = useProvider ? ValueProviderUtils.buildDataByProvider(triggers, triggerList) : triggerList;
        return new EasyuiPageOutput((int) total, results);
    }

    /**
     * 根据条件聚合查询触发点及模板信息
     *
     * @param triggers
     * @return
     */
    @Override
    public List<TriggersVo> selectForUnionTemplate(Triggers triggers) {
        return getActualMapper().selectForUnionTemplate(triggers);
    }

    @Override
    public BaseOutput updateEnable(Long id, Boolean enable) {
        Triggers triggers = new Triggers();
        triggers.setId(id);
        if (enable) {
            triggers.setEnabled(TriggersEnum.EnabledStateEnum.ENABLED.getCode());
        } else {
            triggers.setEnabled(TriggersEnum.EnabledStateEnum.DISABLED.getCode());
        }
        this.updateSelective(triggers);
        return BaseOutput.success("操作成功");
    }

    @Override
    public Boolean checkNotExist(String marketCode, String systemCode, String sceneCode,Long selfId) {
        Triggers triggers = new Triggers();
        triggers.setMarketCode(marketCode);
        triggers.setSystemCode(systemCode);
        triggers.setSceneCode(sceneCode);
        List<Triggers> list = this.list(triggers);
        if (CollectionUtil.isEmpty(list)) {
            return true;
        } else {
            if (list.size() > 1) {
                return false;
            }
            if (null != selfId) {
                Triggers temp = list.get(0);
                if (temp.getId().equals(selfId)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseOutput saveInfo(TriggersVo triggersVo) {
        if (null == triggersVo){
            return BaseOutput.failure("数据为空");
        }
        Boolean notExist = this.checkNotExist(triggersVo.getMarketCode(), triggersVo.getSystemCode(), triggersVo.getSceneCode(), triggersVo.getId());
        if (!notExist) {
            return BaseOutput.failure("市场-系统-场景对应关系已存在，不能重复添加");
        }
        String triggerCode = "";
        /**
         * ID为空，则为新增操作，ID不为空，则为修改操作
         */
        if (null == triggersVo.getId()){
            triggerCode = sequenceNumberService.getBizNumberByType(BizNumberTypeEnum.TRIGGERS);
            triggersVo.setTriggerCode(triggerCode);
            Triggers triggers = new Triggers();
            BeanUtil.copyProperties(triggersVo,triggers);
            triggers.setEnabled(TriggersEnum.EnabledStateEnum.DISABLED.getCode());
            this.insertSelective(triggers);
        }else {
            Triggers old = this.get(triggersVo.getId());
            triggerCode = old.getTriggerCode();
            old.setSystemCode(triggersVo.getSystemCode());
            old.setMarketCode(triggersVo.getMarketCode());
            old.setSceneCode(triggersVo.getSceneCode());
            old.setWhitelist(triggersVo.getWhitelist());
            this.updateSelective(old);
            triggersTemplateService.deleteByTriggerCode(triggerCode);
        }
        final String code = triggerCode;
        List<TriggersTemplate> templateList = triggersVo.getTemplateList();
        if (CollectionUtil.isNotEmpty(templateList)){
            templateList.stream().forEach(t->{
                t.setTriggerCode(code);
                t.setCreated(new Date());
                t.setModified(new Date());
                t.setMarketChannelIds(produceChannelIds(t.getMarketChannelIds()));
            });
            triggersTemplateService.batchInsert(templateList);
        }
        return BaseOutput.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        Triggers triggers = this.get(id);
        if (null == triggers){
            return 0;
        }
        triggersTemplateService.deleteByTriggerCode(triggers.getTriggerCode());
        return super.delete(id);
    }

    /**
     * 生成存储的channelIds
     * @param params
     * @return
     */
    private String produceChannelIds(String params) {
        if (StrUtil.isNotBlank(params)) {
            if (params.indexOf(",") < 0) {
                params = "[" + params + "]";
            }
            return JSON.parseArray(params).stream().filter(Objects::nonNull).map(Object::toString).filter(StrUtil::isNotBlank).collect(Collectors.joining(","));
        }
        return null;
    }
}