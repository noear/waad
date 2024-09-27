package waad_demo.config;

import org.noear.waad.cache.ICacheServiceEx;
import org.noear.waad.cache.LocalCache;
import org.noear.waad.cache.SecondCache;
import org.noear.waad.cache.memcached.MemCache;

/**
 * Created by noear on 2017/7/22.
 */
public class CacheUtil {
    public static boolean isUsingCache;

    ICacheServiceEx cache1 = new MemCache("text", 60, "127.0.0.0:8001",null,null);
    ICacheServiceEx cache2 = new LocalCache("xxxx", 60);

    //二级缓存
    ICacheServiceEx cachex = new SecondCache(cache1,cache2);


}
