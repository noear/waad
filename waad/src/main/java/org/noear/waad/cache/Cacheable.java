package org.noear.waad.cache;

/**
 * 可缓存的
 *
 * @author noear
 * @since 14-6-12
 */
public interface Cacheable {
    /**
     * 缓存标识
     */
    String getWaadKey();
}
