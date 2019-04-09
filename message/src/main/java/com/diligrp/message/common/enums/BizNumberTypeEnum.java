package com.diligrp.message.common.enums;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/3 16:46
 */
public enum BizNumberTypeEnum {

    TRIGGERS("trigger", "触发点编码", "TRI",5, "%Y%m%d"),
    REQUEST("request", "操作请求码", "REQ", 6, "%Y%m%d"),
    ;

    private String type;
    private String name;
    private String prefix;
    private int length;
    private String dbFormat;

    /**
     * @param type  编码业务类型
     * @param name  名称
     * @param prefix 默认前缀
     * @param length    编号长度
     * @param dbFormat 数据库格式化的日期格式
     */
    BizNumberTypeEnum(String type, String name, String prefix,int length,String dbFormat){
        this.type = type;
        this.name = name;
        this.prefix = prefix;
        this.length = length;
        this.dbFormat = dbFormat;
    }

    public static BizNumberTypeEnum getBizNumberByType(String type) {
        for (BizNumberTypeEnum bizNumberType : BizNumberTypeEnum.values()) {
            if (bizNumberType.getType().equals(type)) {
                return bizNumberType;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getPrefix() {
        return prefix;
    }
    public int getLength() {
        return length;
    }
    public String getDbFormat() {
        return dbFormat;
    }
}
