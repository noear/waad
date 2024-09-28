package org.noear.waad.cache;

import org.noear.waad.WaadConfig;
import org.noear.waad.util.function.Fun1;
import org.noear.waad.util.function.Fun1Ex;

import java.lang.reflect.Type;

/**
 * 缓存服务扩展接口
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


    ////////////////////////////

    /**
     * 缓存标签管理器
     */
    default CacheTags tags() {
        return new CacheTags(this);
    }

    /**
     * 清空缓存
     */
    default void clear(String tag) {
        tags().clear(tag);
    }

    /**
     * 更新缓存
     */
    default <T> void update(String tag, Fun1<T, T> setter) {
        tags().update(tag, Object.class, setter);
    }

    /**
     * 更新缓存
     */
    default <T> void update(String tag, Class<T> clz, Fun1<T, T> setter) {
        tags().update(tag, clz, setter);
    }

    /**
     * 获取
     */
    default <T, E extends Throwable> T getBy(String key, Fun1Ex<T, CacheUsing, E> builder) throws E {
        return getBy(getDefalutSeconds(), key, builder);
    }

    /**
     * 获取
     */
    default <T, E extends Throwable> T getBy(String key, Class<T> clz, Fun1Ex<T, CacheUsing, E> builder) throws E {
        return getBy(getDefalutSeconds(), key, clz, builder);
    }

    /**
     * 获取
     */
    default <T, E extends Throwable> T getBy(int seconds, String key, Fun1Ex<T, CacheUsing, E> builder) throws E {
        CacheUsing cu = new CacheUsing(this);
        return cu.usingCache(seconds).getEx(key, Object.class, () -> builder.run(cu));
    }

    /**
     * 获取
     */
    default <T, E extends Throwable> T getBy(int seconds, String key, Class<T> clz, Fun1Ex<T, CacheUsing, E> builder) throws E {
        CacheUsing cu = new CacheUsing(this);
        return cu.usingCache(seconds).getEx(key, clz, () -> builder.run(cu));
    }

    /**
     * 名字设置（自动注册到cache库）
     */
    default ICacheService nameSet(String name) {
        if (name != null) {
            WaadConfig.libOfCache.put(name, this);
        }

        return this;
    }
}