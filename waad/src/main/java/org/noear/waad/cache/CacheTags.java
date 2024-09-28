package org.noear.waad.cache;

import org.noear.waad.util.function.Fun1;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存标签
 *
 * @author noear
 * @since 3.0
 */
public class CacheTags {
    private ICacheService _Cache;

    public CacheTags(ICacheService caching) {
        _Cache = caching;
    }

    //#region 异步 Add

    private List<String> asynTags = null;

    /**
     * 为缓存添加一个标签（异步 Add{begin}）
     *
     * @param tag 标签
     */
    public void beginAdd(String tag) {
        if (asynTags == null)
            asynTags = new ArrayList<String>();

        asynTags.add(tag);
    }

    /**
     * 为缓存添加一个标签（异步 Add{end}）
     *
     * @param target 目标
     */
    public void endAdd(Cacheable target) {
        endAdd(target.getWaadKey());
    }

    /**
     * 为缓存添加一个标签（异步 Add{end}）
     *
     * @param targetCacheKey 目标缓存键
     */
    public void endAdd(String targetCacheKey) {
        if (asynTags == null)
            return;

        if (targetCacheKey != null && targetCacheKey.length() > 0) {
            for (String tag : asynTags)
                add(tag, targetCacheKey);
        }
        asynTags.clear();
    }
    //#endregion
    //#region 同步 Add

    /**
     * 为缓存添加一个标签（同步 Add）
     *
     * @param tag            标签
     * @param targetCacheKey 目标缓存键
     */
    public void add(String tag, String targetCacheKey) {
        List<String> temp = $(KEY(tag));
        if (temp.contains(targetCacheKey))
            return;

        temp.add(targetCacheKey);

        $(KEY(tag), temp);
    }

    //#endregion


    /**
     * 获取标签里的缓存 key 数量
     */
    public int count(String tag) {
        return $(KEY(tag)).size();
    }

    /**
     * 清空 tag 相关的所有缓存
     */
    public CacheTags clear(String tag) {
        List<String> keys = $(KEY(tag));

        for (String cacheKey : keys)
            _Cache.remove(cacheKey);

        _Cache.remove(KEY(tag));

        return this;
    }

    /**
     * 更新缓存
     *
     * @param tag     标签
     * @param clz     类型
     * @param handler 处理器
     */
    public <T extends Object> void update(String tag, Class<?> clz, Fun1<T, T> handler) {
        List<String> keys = getCacheKeys(tag);

        for (String key : keys) {
            Object temp = _Cache.get(key, clz);
            if (temp == null) {
                continue;
            }

            try {
                T obj = (T) temp;
                if (obj != null) {
                    obj = handler.run(obj);
                    _Cache.store(key, obj, _Cache.getDefalutSeconds());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 获取标签里的缓存 key
     *
     * @param tag   标签
     * @param index 序位
     */
    public String getCacheKey(String tag, int index) {
        List<String> temp = $(KEY(tag));

        if (temp.size() > index)
            return temp.get(index);
        else
            return null;
    }

    /**
     * 获取一个标签里的内容
     *
     * @param tag 标签
     */
    public List<String> getCacheKeys(String tag) {
        return $(KEY(tag));
    }

    /**
     * 移除标签里的缓存 key
     *
     * @param tag    标签
     * @param target 目标
     */
    public void removeCacheKey(String tag, Cacheable target) {
        removeCacheKey(tag, (target.getWaadKey()));
    }

    /**
     * 移除标签里的缓存 key
     *
     * @param tag            标签
     * @param targetCacheKey 目标缓存键
     */
    public void removeCacheKey(String tag, String targetCacheKey) {
        List<String> temp = $(KEY(tag));
        temp.remove(targetCacheKey);
        $(KEY(tag), temp);

        _Cache.remove(targetCacheKey);
    }

    private List<String> $(String key) {
        Object temp = _Cache.get(key, ArrayList.class);

        if (temp == null)
            return new ArrayList<String>();
        else
            return (List<String>) temp;
    }

    private void $(String key, List<String> value) {
        _Cache.store(key, value, _Cache.getDefalutSeconds());
    }

    private String KEY(String tag) {
        return ("@" + tag).toUpperCase();
    }
}