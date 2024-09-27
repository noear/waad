package org.noear.waad.cache;

import org.noear.waad.IWaadKey;
import org.noear.waad.ext.Act0;
import org.noear.waad.ext.Act2;
import org.noear.waad.ext.Fun0;
import org.noear.waad.ext.Fun0Ex;

/**
 * 缓存使用控制接口
 *
 * @author noear
 * @since 3.0
 */
public class CacheUsing implements ICacheUsing<CacheUsing>, IWaadKey {
    //#region ICacheControllerState 成员
    public ICacheService outerCaching;
    public int cacheSeconds;
    public CacheState cacheController;
    private Object cacheCondition; //Fun1<Boolean,T>

    //#endregion

    //#region ICacheUsing<Q> 成员

    public CacheUsing usingCache(boolean isCache) {
        this.cacheController = (isCache ? CacheState.Using : CacheState.NonUsing);
        return this;
    }

    public CacheUsing usingCache(int seconds) {
        if (this.cacheController != CacheState.Refurbish)
            this.cacheController = CacheState.Using;

        this.cacheSeconds = seconds;
        return this;
    }

    public <T> CacheUsing usingCache(Act2<CacheUsing, T> condition) {
        if (condition != null) {

            if (this.cacheController != CacheState.Refurbish)
                this.cacheController = CacheState.Using;

            this.cacheCondition = condition;
        }

        return this;
    }

    public CacheUsing refurbishCache() {
        this.cacheController = CacheState.Refurbish;
        return this;
    }

    public CacheUsing refurbishCache(boolean isRefubish) {
        this.cacheController = (isRefubish ? CacheState.Refurbish : CacheState.Using);
        return this;
    }

    public CacheUsing removeCache() {
        this.cacheController = CacheState.Remove;
        return this;
    }

    //#endregion
    //===================================
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
    /// <summary>
    /// 获取一个执行结果
    /// </summary>
    /// <typeparam name="T">实体类型</typeparam>
    /// <param name="exec">执行方法</param>
    /// <param name="waadKey">缓存关健字</param>
    /// <returns></returns>

    public <T> T get(String waadKey, Fun0<T> exec) {
        return get(waadKey, Object.class, exec);
    }

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

    public <T> T getOnly(String waadKey) {
        return getOnly(waadKey, Object.class);
    }


    public <T> T getOnly(String waadKey, Class<?> clz) {
        _waadKey = waadKey;

        return (T) outerCaching.get(_waadKey, clz);
    }

    public void storeOnly(String waadKey, Object data) {
        _waadKey = waadKey;

        if (data != null) {
            outerCaching.store(_waadKey, data, cacheSeconds > 0 ? cacheSeconds : outerCaching.getDefalutSeconds());
        }
    }

    //============
    CacheTags cacheTags = null;
    Act0 onExecH = null;

    /// <summary>
    /// 添加缓存标签 (统一缓存维护,以便统一删除和直接获取)
    /// </summary>
    /// <param name="tag">标签名</param>
    /// <param name="val">标签值</param>
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


    /// <summary>
    /// 根据缓存标签清除缓存
    /// </summary>
    /// <param name="tag">标签名</param>
    /// <param name="val">标签值</param>
    public void clearCache(String tag) {
        if (cacheTags != null)
            cacheTags.clear(tag);
    }
}
