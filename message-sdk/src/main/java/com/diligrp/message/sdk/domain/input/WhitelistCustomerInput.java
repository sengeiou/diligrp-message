package com.diligrp.message.sdk.domain.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
     * 客户ID
     */
    @NotNull(message = "客户ID不能为空")
    private Long id;
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
     * 开始时间
     */
    @NotNull(message = "白名单开始时间不能为空")
    private LocalDate startDate;

    /**
     * 结束时间
     */
    @NotNull(message = "白名单结束时间不能为空")
    private LocalDate endDate;

}
