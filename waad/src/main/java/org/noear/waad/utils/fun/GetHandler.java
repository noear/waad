package org.noear.waad.utils.fun;

/**
 * Created by noear on 14-6-13.
 * 数据获取代理
 */
@FunctionalInterface
public interface GetHandler {
    Object get(String name);
}
