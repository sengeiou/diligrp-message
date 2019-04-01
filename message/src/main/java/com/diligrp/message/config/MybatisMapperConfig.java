package com.diligrp.message.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/3/31 9:57
 */
@Configuration
@MapperScan(basePackages = {"com.diligrp.message.mapper", "com.dili.ss.dao"})
public class MybatisMapperConfig {
}
