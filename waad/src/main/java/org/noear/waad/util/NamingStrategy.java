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
    public String classToTableName(Class<?> clz) {
        if (WaadConfig.isUsingUnderlineColumnName) {
            return NamingUtils.toUnderlineString(clz.getSimpleName());
        } else {
            return clz.getSimpleName();
        }
    }

    public String fieldToColumnName(Class<?> clz, Field f) {
        if (WaadConfig.isUsingUnderlineColumnName) {
            return NamingUtils.toUnderlineString(f.getName());
        } else {
            return f.getName();
        }
    }
}
