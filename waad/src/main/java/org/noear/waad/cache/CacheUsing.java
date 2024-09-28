package org.noear.waad.cache;

import org.noear.waad.util.function.Act0;
import org.noear.waad.util.function.Act2;
import org.noear.waad.util.function.Fun0;
import org.noear.waad.util.function.Fun0Ex;

/**
 * 缓存使用控制接口
 *
 * @author noear
 */
public class CacheUsing implements ICacheUsing<CacheUsing>, Cacheable {
    //#region ICacheControllerState 成员
    protected ICacheService outerCaching;
    protected int cacheSeconds;
    protected CacheState cacheController;

    private Object cacheCondition; //Fun1<Boolean,T>

    //#endregion

    //#region ICacheUsing<Q> 成员

    /**
     * 使用缓存
     */
    public CacheUsing usingCache(boolean isCache) {
        this.cacheController = (isCache ? CacheState.Using : CacheState.NonUsing);
        return this;
    }

    /**
     * 使用缓存
     */
    public CacheUsing usingCache(int seconds) {
        if (this.cacheController != CacheState.Refurbish)
            this.cacheController = CacheState.Using;

        this.cacheSeconds = seconds;
        return this;
    }

    /**
     * 使用缓存
     */
    public <T> CacheUsing usingCache(Act2<CacheUsing, T> condition) {
        if (condition != null) {

            if (this.cacheController != CacheState.Refurbish)
                this.cacheController = CacheState.Using;

            this.cacheCondition = condition;
        }

        return this;
    }

    /**
     * 刷新缓存
     */
    public CacheUsing refurbishCache() {
        this.cacheController = CacheState.Refurbish;
        return this;
    }


    /**
     * 刷新缓存
     */
    public CacheUsing refurbishCache(boolean isRefubish) {
        this.cacheController = (isRefubish ? CacheState.Refurbish : CacheState.Using);
        return this;
    }

    public CacheUsing removeCache() {
        this.cacheController = CacheState.Remove;
        return this;
    }

    //===================================
    public String _waadKey = null;

    public String getWaadKey() {
        return _waadKey;
    }

    public CacheUsing(ICacheService cache) {
        this.outerCaching = cache;
        this.cacheController = CacheState.Using;
    }

    //===========

    /**
     * 获取一个执行结果
     *
     * @param waadKey 缓存关健字
     * @param exec    执行方法
     * @return 实体
     */
    public <T> T get(String waadKey, Fun0<T> exec) {
        return get(waadKey, Object.class, exec);
    }

    /**
     * 获取一个执行结果
     *
     * @param waadKey 缓存关健字
     * @param clz     实体类型
     * @param exec    执行方
     * @return 实体
     */
    public <T> T get(String waadKey, Class<?> clz, Fun0<T> exec) {
        if (this.cacheController == CacheState.NonUsing)
            return exec.run();

        _waadKey = waadKey;

        T cacheT = null;

        if (this.cacheController == CacheState.Using) //如果是刷新，则不从缓存获取
            cacheT = (T) outerCaching.get(_waadKey, clz);

        if (cacheT == null) {
            cacheT = exec.run();

            if (cacheT != null) {
                if (cacheCondition != null)  //如果有缓存条件，则使用检查
                    ((Act2<CacheUsing, T>) cacheCondition).run(this, cacheT);

                if (cacheController != CacheState.NonUsing) {
                    outerCaching.store(_waadKey, cacheT, cacheSeconds > 0 ? cacheSeconds : outerCaching.getDefalutSeconds());
                }
            }
        }

        if (onExecH != null)
            onExecH.run();

        return cacheT;
    }

    public <T, E extends Throwable> T getEx(String waadKey, Fun0Ex<T, E> exec) throws E {
        return getEx(waadKey, Object.class, exec);
    }

    public <T, E extends Throwable> T getEx(String waadKey, Class<?> clz, Fun0Ex<T, E> exec) throws E {
        if (this.cacheController == CacheState.NonUsing)
            return exec.run();

        _waadKey = waadKey;

        T cacheT = null;

        if (this.cacheController == CacheState.Using) //如果是刷新，则不从缓存获取
            cacheT = (T) outerCaching.get(_waadKey, clz);

        if (cacheT == null) {
            cacheT = exec.run();

            if (cacheT != null) {
                if (cacheCondition != null)  //如果有缓存条件，则使用检查
                    ((Act2<CacheUsing, T>) cacheCondition).run(this, cacheT);

                if (cacheController != CacheState.NonUsing) {
                    outerCaching.store(_waadKey, cacheT, cacheSeconds > 0 ? cacheSeconds : outerCaching.getDefalutSeconds());
                }
            }
        }

        if (onExecH != null)
            onExecH.run();

        return cacheT;
    }


    /**
     * 只获取
     *
     * @param waadKey 缓存键
     */

    public <T> T getOnly(String waadKey) {
        return getOnly(waadKey, Object.class);
    }


    /**
     * 只获取（特定类型）
     *
     * @param waadKey 缓存键
     * @param clz     特定类型
     */
    public <T> T getOnly(String waadKey, Class<?> clz) {
        _waadKey = waadKey;

        return (T) outerCaching.get(_waadKey, clz);
    }

    /**
     * 只存储
     */
    public void storeOnly(String waadKey, Object data) {
        _waadKey = waadKey;

        if (data != null) {
            outerCaching.store(_waadKey, data, cacheSeconds > 0 ? cacheSeconds : outerCaching.getDefalutSeconds());
        }
    }

    //============
    private CacheTags cacheTags = null;
    private Act0 onExecH = null;

    /**
     * 添加缓存标签 (统一缓存维护,以便统一删除和直接获取)
     *
     * @param tag 标签名
     */
    public CacheUsing cacheTag(String tag) {
        if (cacheTags == null) {
            cacheTags = new CacheTags(this.outerCaching);

            onExecH = () -> {
                cacheTags.endAdd(this);
            };
        }

        cacheTags.beginAdd(tag);
        return this;
    }

    /**
     * 根据缓存标签清除缓存
     *
     * @param tag 标签名
     */
    public void clearCache(String tag) {
        if (cacheTags != null)
            cacheTags.clear(tag);
    }
}