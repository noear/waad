package org.noear.waad.util;


import org.noear.waad.WaadConfig;

import java.lang.reflect.Field;

/**
 * 命名策略
 *
 * @author noear
 * @since 3.2
 * */
public class NamingStrategy {
    /**
     * 类转为表名
     *
     * @param clz 实体类
     */
    public String classToTableName(Class<?> clz) {
        if (WaadConfig.isUsingUnderlineColumnName) {
            return NamingUtils.toUnderlineString(clz.getSimpleName());
        } else {
            return clz.getSimpleName();
        }
    }

    /**
     * 字段转为列名
     *
     * @param clz 实体类
     * @param f   字段
     */
    public String fieldToColumnName(Class<?> clz, Field f) {
        if (WaadConfig.isUsingUnderlineColumnName) {
            return NamingUtils.toUnderlineString(f.getName());
        } else {
            return f.getName();
        }
    }
}