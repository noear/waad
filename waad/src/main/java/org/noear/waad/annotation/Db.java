package org.noear.waad.annotation;

import java.lang.annotation.*;

/**
 * 为Waad对象，指定数据源（如 Mapper）
 *
 * @author noear
 * @since 3.2
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Documented
public @interface Db {
    String value() default "";
}