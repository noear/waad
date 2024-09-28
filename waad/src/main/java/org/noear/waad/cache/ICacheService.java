package org.noear.waad.cache;

import java.lang.reflect.Type;

/**
 * 缓存服务接口
 *
 * @author noear
 * @since 3.0
 */
public interface ICacheService {
    /**
     * 保存
     */
    void store(String key, Object obj, int seconds);

    /**
     * 获取
     */
    <T> T get(String key, Type type);

    /**
     * 获取
     */
    default <T> T get(String key, Class<T> clz) {
        return get(key, (Type) clz);
    }

    /**
     * 移除
     */
    void remove(String key);

    /**
     * 默认缓存时间
     */
    int getDefalutSeconds();

    /**
     * 缓存键的开头字符
     */
    String getCacheKeyHead();
}