package org.noear.waad.annotation;

import java.lang.annotation.*;

/**
 * 表标识; 可继承
 *
 * @author noear
 * @since 3.2
 * */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String value(); //别名
}
