package org.noear.waad.util;

import org.noear.waad.annotation.PrimaryKey;

import java.lang.reflect.Field;

/**
 * 主键策略
 *
 * @author noear
 * @since 3.2
 * */
public class PrimaryKeyStrategy {
    /**
     * 检测字段是否为主键
     *
     * @param clz 实体类
     * @param f   字段
     */
    public boolean fieldIsPrimaryKey(Class<?> clz, Field f) {
        PrimaryKey annotation = f.getAnnotation(PrimaryKey.class);
        return annotation != null;
    }
}