package com.diligrp.message.domain.vo;

import com.dili.ss.domain.annotation.Operator;
import com.dili.ss.dto.IMybatisForceParams;
import com.diligrp.message.domain.Whitelist;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WhitelistVo extends Whitelist implements IMybatisForceParams {
    /**
     *
     * 权限市场
     * */
    @Operator(Operator.IN)
    @Column(name = "`market_code`")
    private List<String> authMarkets;

    /**
     * 发送时间  -- 开始
     */
    @Column(name = "`start_date`")
    @Operator(Operator.GREAT_EQUAL_THAN)
    private Date startTime;

    /**
     * 发送时间 -- 结束
     */

    @Column(name = "`endDate`")
    @Operator(Operator.LITTLE_EQUAL_THAN)
    private Date endTime;

    /**
     * 关键字查询
     * @return
     */
    @Transient
    private String keywords;


    /**
     *
     * 以下字段时实现接口方法
     * */
    @Transient
    private String whereSuffixSql;

    @Transient
    private Map<String, Object> setForceParams;

    @Transient
    private Map<String, Object> insertForceParams;

    @Transient
    private Set<String> selectColumns;

    @Transient
    private Boolean checkInjection;


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getAuthMarkets() {
        return authMarkets;
    }

    public void setAuthMarkets(List<String> authMarkets) {
        this.authMarkets = authMarkets;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    @Override
    public Map<String, Object> getSetForceParams() {
        return setForceParams;
    }

    @Override
    public void setSetForceParams(Map<String, Object> setForceParams) {
        this.setForceParams = setForceParams;
    }

    @Override
    public Map<String, Object> getInsertForceParams() {
        return insertForceParams;
    }

    @Override
    public void setInsertForceParams(Map<String, Object> insertForceParams) {
        this.insertForceParams = insertForceParams;
    }

    @Override
    public Set<String> getSelectColumns() {
        return selectColumns;
    }

    @Override
    public void setSelectColumns(Set<String> selectColumns) {
        this.selectColumns = selectColumns;
    }

    @Override
    public Boolean getCheckInjection() {
        return checkInjection;
    }

    @Override
    public void setCheckInjection(Boolean checkInjection) {
        this.checkInjection = checkInjection;
    }

    @Override
    public String getWhereSuffixSql() {
        return whereSuffixSql;
    }

    @Override
    public void setWhereSuffixSql(String whereSuffixSql) {
        this.whereSuffixSql = whereSuffixSql;
    }
}
