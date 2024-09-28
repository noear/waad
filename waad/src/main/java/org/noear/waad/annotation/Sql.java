package org.noear.waad.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SQL 注解
 *
 * @author noear
 * @since 3.2
 * */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sql {
    /**
     * 代码
     */
    String value() default "";

    /**
     * 缓存服务
     */
    String caching() default "";

    /**
     * 清除缓存
     */
    String cacheClear() default "";

    /**
     * 缓存标签
     */
    String cacheTag() default "";

    /**
     * 缓存时间
     */
    int usingCache() default 0;
}
