package org.noear.waad.cache;

import java.lang.reflect.Type;

/**
 * 空缓存服务
 *
 * @author noear
 * @since 3.0
 */
public class EmptyCache implements ICacheServiceEx {
    /**
     * 存储
     */
    @Override
    public void store(String s, Object o, int i) {

    }

    /**
     * 获取
     */
    @Override
    public <T> T get(String s, Type type) {
        return null;
    }

    /**
     * 移除
     */
    @Override
    public void remove(String s) {

    }

    /**
     * 默认缓存秒数
     */
    @Override
    public int getDefalutSeconds() {
        return 0;
    }

    /**
     * 缓存键头
     */
    @Override
    public String getCacheKeyHead() {
        return "";
    }
}