package org.noear.waad.util.function;

/**
 * Created by noear on 14-6-13.
 * 数据获取代理
 */
@FunctionalInterface
public interface GetHandler {
    Object get(String name);
}
