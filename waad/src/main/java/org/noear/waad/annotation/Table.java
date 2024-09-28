package org.noear.waad.annotation;

import java.lang.annotation.*;

/**
 * 表（可继承）
 *
 * @author noear
 * @since 3.2
 * */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 表名
     */
    String value();
}
