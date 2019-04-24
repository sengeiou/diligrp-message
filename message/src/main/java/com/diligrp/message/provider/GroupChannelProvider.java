package com.diligrp.message.provider;

import cn.hutool.core.util.StrUtil;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValueProvider;
import com.diligrp.message.common.enums.MessageEnum;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/10 16:05
 */
@Component
public class GroupChannelProvider implements ValueProvider {

    @Override
    public List<ValuePair<?>> getLookupList(Object val, Map metaMap, FieldMeta fieldMeta) {
        return null;
    }

    @Override
    public String getDisplayText(Object object, Map metaMap, FieldMeta fieldMeta) {
        if (null == object){
            return null;
        }
        String str = String.valueOf(object);
        if ("--".equals(str)){
            return str;
        }
        String[] split = str.split(",");
        Set<String> sets = Sets.newHashSet(split);
        StringBuffer strb = new StringBuffer();
        for (String s : sets) {
            strb.append(MessageEnum.ChannelEnum.getChannelEnum(s).getName()).append(" ");
        }
        return strb.toString();
    }
}
