package com.diligrp.message.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.dili.logger.sdk.annotation.BusinessLogger;
import com.dili.logger.sdk.util.LoggerUtil;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.metadata.ValueProviderUtils;
import com.dili.ss.util.POJOUtils;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;
import com.diligrp.message.common.enums.BizNumberTypeEnum;
import com.diligrp.message.common.enums.TriggersEnum;
import com.diligrp.message.constants.MessageConstant;
import com.diligrp.message.domain.Triggers;
import com.diligrp.message.domain.TriggersTemplate;
import com.diligrp.message.domain.input.TriggersSaveInput;
import com.diligrp.message.domain.input.TriggersTemplateSaveInput;
import com.diligrp.message.domain.vo.TriggersVo;
import com.diligrp.message.mapper.TriggersMapper;
import com.diligrp.message.service.TriggersService;
import com.diligrp.message.service.TriggersTemplateService;
import com.diligrp.message.service.remote.MarketRpcService;
import com.diligrp.message.service.remote.UidRpcService;
import com.diligrp.message.utils.MessageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2019-03-31 10:52:31.
 * @author yuehongbo
 */
@RequiredArgsConstructor
@Service
public class TriggersServiceImpl extends BaseServiceImpl<Triggers, Long> implements TriggersService {

    private TriggersMapper getActualMapper() {
        return (TriggersMapper)getDao();
    }

    private final MarketRpcService marketRpcService;
    private final TriggersTemplateService triggersTemplateService;
    private final UidRpcService uidRpcService;

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
            List<String> currentUserFirms = marketRpcService.getCurrentUserFirmCodes();
            if(CollectionUtil.isEmpty(currentUserFirms)){
                return new EasyuiPageOutput(0L, Collections.emptyList());
            }
            query.setMarketCodeList(currentUserFirms);
        }
        List<TriggersVo> triggerList = getActualMapper().selectForPage(query);
        long total = triggerList instanceof Page ? ((Page) triggerList).getTotal() : (long) triggerList.size();
        List results = useProvider ? ValueProviderUtils.buildDataByProvider(triggers, triggerList) : triggerList;
        return new EasyuiPageOutput(total, results);
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
    @BusinessLogger(systemCode = MessageConstant.SYSTEM_CODE, businessType = "message_triggers_scene")
    public BaseOutput updateEnable(Long id, Boolean enable) {
        Triggers triggers = this.get(id);
        String content = null;
        String operationType = null;
        if (enable) {
            operationType = "enable";
            content = "启用了短信场景" + triggers.getTriggerCode();
            triggers.setEnabled(TriggersEnum.EnabledStateEnum.ENABLED.getCode());
        } else {
            operationType = "disable";
            content = "禁用了短信场景" + triggers.getTriggerCode();
            triggers.setEnabled(TriggersEnum.EnabledStateEnum.DISABLED.getCode());
        }
        this.update(triggers);
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        LoggerUtil.buildBusinessLoggerContext(triggers.getId(), triggers.getTriggerCode(), userTicket.getId(), userTicket.getRealName(), userTicket.getFirmId(), content, "", operationType);
        return BaseOutput.success("操作成功");
    }

    @Override
    public Boolean checkNotExist(String marketCode, String systemCode, String sceneCode, Long selfId) {
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
    public BaseOutput saveInfo(TriggersSaveInput triggersSaveInput) {
        if (Objects.isNull(triggersSaveInput)) {
            return BaseOutput.failure("数据为空");
        }
        Boolean notExist = this.checkNotExist(triggersSaveInput.getMarketCode(), triggersSaveInput.getSystemCode(), triggersSaveInput.getSceneCode(), triggersSaveInput.getId());
        if (!notExist) {
            return BaseOutput.failure("市场-系统-场景对应关系已存在，不能重复添加");
        }
        String triggerCode = "";
        /**
         * ID为空，则为新增操作，ID不为空，则为修改操作
         */
        if (Objects.isNull(triggersSaveInput.getId())) {
            triggerCode = uidRpcService.getBizNumber(BizNumberTypeEnum.TRIGGERS).orElse(IdUtil.getSnowflake(1, 1).nextIdStr());
            triggersSaveInput.setTriggerCode(triggerCode);
            Triggers triggers = new Triggers();
            BeanUtil.copyProperties(triggersSaveInput, triggers);
            triggers.setEnabled(TriggersEnum.EnabledStateEnum.DISABLED.getCode());
            this.insertSelective(triggers);
        } else {
            Triggers old = this.get(triggersSaveInput.getId());
            triggerCode = old.getTriggerCode();
            old.setSystemCode(triggersSaveInput.getSystemCode());
            old.setMarketCode(triggersSaveInput.getMarketCode());
            old.setSceneCode(triggersSaveInput.getSceneCode());
            old.setWhitelist(triggersSaveInput.getWhitelist());
            old.setBlacklist(triggersSaveInput.getBlacklist());
            this.updateSelective(old);
            triggersTemplateService.deleteByTriggerCode(triggerCode);
        }
        final String code = triggerCode;
        List<TriggersTemplateSaveInput> templateSaveInputList = triggersSaveInput.getTemplateList();
        if (CollectionUtil.isNotEmpty(templateSaveInputList)) {
            List<TriggersTemplate> triggersTemplateList = Lists.newArrayListWithExpectedSize(templateSaveInputList.size());
            templateSaveInputList.stream().forEach(t -> {
                TriggersTemplate triggersTemplate = BeanUtil.copyProperties(t, TriggersTemplate.class);
                triggersTemplate.setTriggerCode(code);
                triggersTemplate.setCreated(MessageUtil.now());
                triggersTemplate.setModified(MessageUtil.now());
                triggersTemplate.setMarketChannelIds(ArrayUtil.join(t.getAccessKeyIds(), ","));
                triggersTemplateList.add(triggersTemplate);
            });
            triggersTemplateService.batchInsert(triggersTemplateList);
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
}