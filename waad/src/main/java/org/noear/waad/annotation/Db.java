package org.noear.waad.annotation;

import java.lang.annotation.*;

/**
 * 为 Waad 对象，指定数据源（如 Mapper）
 *
 * @author noear
 * @since 3.2
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Documented
public @interface Db {
    /**
     * 数据源名
     */
    String value() default "";
}