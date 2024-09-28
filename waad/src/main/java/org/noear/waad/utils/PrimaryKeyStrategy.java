package org.noear.waad.utils;

import org.noear.waad.annotation.PrimaryKey;

import java.lang.reflect.Field;

/**
 * 主键策略
 *
 * @author noear
 * @since 3.2
 * */
public class PrimaryKeyStrategy {
    public boolean fieldIsPrimaryKey(Class<?> clz, Field f) {
        PrimaryKey annotation = f.getAnnotation(PrimaryKey.class);
        return annotation != null;
    }
}
