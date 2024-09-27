package org.noear.waad.cache;

import org.noear.waad.IWaadKey;
import org.noear.waad.ext.Fun1;

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

    public CacheTags(ICacheService caching)
    {
        _Cache = caching;
    }

    //#region 异步 Add
    private List<String> asynTags = null;
    /// <summary>
    /// 为缓存添加一个标签（异步 Add{begin}）
    /// </summary>
    /// <param name="tag">标签</param>
    /// <param name="val">标签值</param>
    public void beginAdd(String tag)
    {
        if (asynTags == null)
            asynTags = new ArrayList<String>();

        asynTags.add(tag);
    }

    /// <summary>
    /// 为缓存添加一个标签（异步 Add{end}）
    /// </summary>
    /// <param name="target">目标</param>
    public void endAdd(IWaadKey target)
    {
        endAdd(target.getWaadKey());
    }

    /// <summary>
    /// 为缓存添加一个标签（异步 Add{end}）
    /// </summary>
    /// <param name="targetCacheKey">目标缓存键</param>
    public void endAdd(String targetCacheKey)
    {
        if (asynTags == null)
            return;

        if (targetCacheKey!=null && targetCacheKey.length()>0)
        {
            for (String tag : asynTags)
                add(tag, targetCacheKey);
        }
        asynTags.clear();
    }
    //#endregion
    //#region 同步 Add


    /// <summary>
    /// 为缓存添加一个标签（同步 Add）
    /// </summary>
    /// <param name="tag">标签</param>
    /// <param name="val">标签值</param>
    /// <param name="targetCacheKey">目标缓存键</param>
    public void add(String tag,  String targetCacheKey)
    {
        List<String> temp = $(KEY(tag));
        if (temp.contains(targetCacheKey))
            return;

        temp.add(targetCacheKey);

        $(KEY(tag), temp);
    }

    //#endregion



    /// <summary>
    /// 清空[@tag=val]相关的所有缓存
    /// </summary>
    public CacheTags clear(String tag)
    {
        List<String> keys = $(KEY(tag));

        for (String cacheKey : keys)
            _Cache.remove(cacheKey);

        _Cache.remove(KEY(tag));

        return this;
    }

    public <T extends Object> void update(String tag, Class<?> clz,  Fun1<T,T> setter) {
        List<String> keys = getCacheKeys(tag);

        for (String key : keys) {
            Object temp = _Cache.get(key, clz);
            if (temp == null) {
                continue;
            }

            try {
                T obj = (T) temp;
                if (obj != null) {
                    obj = setter.run(obj);
                    _Cache.store(key, obj, _Cache.getDefalutSeconds());
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public int count(String tag)
    {
        return $(KEY(tag)).size();
    }


    public String getCacheKey(String tag,  int index)
    {
        List<String> temp = $(KEY(tag));

        if (temp.size() > index)
            return temp.get(index);
        else
            return null;
    }


    /// <summary>
    /// 获取一个标签里的内容
    /// </summary>
    /// <param name="tag"></param>
    /// <param name="val"></param>
    /// <returns></returns>
    public List<String> getCacheKeys(String tag)
    {
        return $(KEY(tag));
    }


    public void removeTag(String tag, String val, IWaadKey target)
    {
        removeTag(tag, val, (target.getWaadKey()));
    }

    public void removeTag(String tag, String val, String targetCacheKey)
    {
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

    private void $(String key, List<String> value)
    {
        _Cache.store(key, value, _Cache.getDefalutSeconds());
    }

    private String KEY(String tag)
    {
        return ("@" + tag).toUpperCase();
    }
}
