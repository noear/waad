package org.noear.waad.cache;

/**
 * 缓存状态
 *
 * @author noear
 * @since 3.0
 */
public enum  CacheState {
    /**
     * 不使用
     */
    NonUsing,
    /**
     * 使用
     */
    Using,
    /**
     * 刷新
     */
    Refurbish,
    /**
     * 移除
     */
    Remove,
}
