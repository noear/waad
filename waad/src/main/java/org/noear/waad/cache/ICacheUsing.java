package org.noear.waad.cache;

/**
 * 缓存使用控制接口
 *
 * @author noear
 * @since 3.0
 */
public interface ICacheUsing<Q> {
    /**
     * 使用缓存
     */
    Q usingCache(boolean isCache);

    /**
     * 使用缓存
     */
    Q usingCache(int seconds);

    /**
     * 刷新缓存
     */
    Q refurbishCache();


    /**
     * 刷新缓存
     */
    Q refurbishCache(boolean isRefubish);

    /**
     * 移除缓存
     */
    Q removeCache();
}
