package org.noear.waad.annotation;

import java.lang.annotation.*;

/**
 * 列主键标识
 *
 * @author noear
 * @since 3.2
 * */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
}
