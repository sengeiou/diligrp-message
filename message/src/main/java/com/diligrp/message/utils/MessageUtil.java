package com.diligrp.message.utils;

import com.dili.ss.util.SpringUtil;
import org.beetl.core.GroupTemplate;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/15 14:25
 */
public class MessageUtil {

    /**
     * 渲染模板数据
     *
     * @param resource 模板内容
     * @param data    填充数据对象
     * @return 渲染后的数据
     */
    public static String produceMsgContent(String resource, Map<String, Object> data) {
        GroupTemplate stringGroupTemplate = SpringUtil.getBean("StringGroupTemplate", GroupTemplate.class);
        org.beetl.core.Template template = stringGroupTemplate.getTemplate(resource);
        template.binding(data);
        return template.render();
    }

    /**
     * 获取当前时间
     * @return 获取当前时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
