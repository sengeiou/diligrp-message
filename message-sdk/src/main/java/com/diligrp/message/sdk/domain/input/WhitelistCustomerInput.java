package com.diligrp.message.sdk.domain.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author yuehongbo
 * @Copyright 本软件源代码版权归农丰时代科技有限公司及其研发团队所有, 未经许可不得任意复制与传播.
 * @date 2020/10/12 14:08
 */
@Getter
@Setter
public class WhitelistCustomerInput {

    /**
     * 客户所属市场
     */
    @NotBlank(message = "客户所属市场不能为空")
    private String marketCode;
    /**
     * 源系统ID
     */
    @NotNull(message = "源ID不能为空")
    @Deprecated
    private Long id;

    /**
     * 源系统ID
     */
    @NotNull(message = "源ID不能为空")
    private Long sourceId;

    /**
     * 客户名称
     */
    @NotBlank(message = "客户名称不能为空")
    private String customerName;
    /**
     * 客户手机号
     */
    @NotBlank(message = "客户手机号不能为空")
    private String cellphone;

    /**
     * 开始日期 -- 废弃，需要用 startDateTime
     */
    @NotNull(message = "白名单开始时间不能为空")
    @Deprecated
    private LocalDate startDate;

    /**
     * 结束 时间 -- 字段废弃 需要用  endDateTime
     */
    @NotNull(message = "白名单结束时间不能为空")
    @Deprecated
    private LocalDate endDate;

    /**
     * 开始时间
     */
    @NotNull(message = "白名单开始时间不能为空")
    private LocalDateTime startDateTime;

    /**
     * 结束时间
     */
    @NotNull(message = "白名单结束时间不能为空")
    private LocalDateTime endDateTime;

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        if (Objects.isNull(startDateTime)) {
            this.setStartDateTime(startDate.atTime(0, 0, 0));
        }
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        if (Objects.isNull(endDateTime)) {
            this.setEndDateTime(endDate.atTime(23, 59, 59));
        }
    }

    public void setId(Long id) {
        this.id = id;
        if (Objects.isNull(sourceId)) {
            this.setSourceId(id);
        }
    }
}
