package org.noear.waad.wrap;


import org.noear.waad.WoodConfig;
import org.noear.waad.utils.NamingUtils;

import java.lang.reflect.Field;

public class NamingStrategy {
    public String classToTableName(Class<?> clz) {
        if (WoodConfig.isUsingUnderlineColumnName) {
            return NamingUtils.toUnderlineString(clz.getSimpleName());
        } else {
            return clz.getSimpleName();
        }
    }

    public String fieldToColumnName(Class<?> clz, Field f) {
        if (WoodConfig.isUsingUnderlineColumnName) {
            return NamingUtils.toUnderlineString(f.getName());
        } else {
            return f.getName();
        }
    }
}
