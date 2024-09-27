package org.noear.waad.wrap;

import org.noear.waad.annotation.PrimaryKey;

import java.lang.reflect.Field;

public class PrimaryKeyStrategy {

    public  boolean fieldIsPrimaryKey(Class<?> clz, Field f) {
        PrimaryKey annotation = f.getAnnotation(PrimaryKey.class);
        return annotation != null;
    }
}
