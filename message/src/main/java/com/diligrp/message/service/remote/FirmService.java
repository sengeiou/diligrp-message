package com.diligrp.message.service.remote;

import com.dili.uap.sdk.domain.Firm;
import com.dili.uap.sdk.domain.dto.FirmDto;

import java.util.List;

/**
 * <B>远程调用获取市场信息</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/1 16:22
 */
public interface FirmService {

    /**
     * 根据条件查询市场
     * @param firm
     * @author wangmi
     * @return
     */
    List<Firm> listByExample(FirmDto firm);

    /**
     * 当前用户拥有访问权限的firmCode
     * @return
     */
    List<String> getCurrentUserFirmCodes();

    /**
     * 当前用户拥有访问权限的firmCode
     * @param userId 用户ID
     * @return
     */
    List<String> getCurrentUserFirmCodes(Long userId);

    /**
     * 获得当前用户拥有的所有Firm
     * @return
     */
    List<Firm> getCurrentUserFirms();

    /**
     * 获得当前用户拥有的所有Firm
     * @param userId 用户ID，如果为空，则从session中获取，如果未获取到，返回空
     * @return
     */
    List<Firm> getCurrentUserFirms(Long userId);
}
