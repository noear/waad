package waad_demo.config;

import org.noear.waad.cache.ICacheService;
import org.noear.waad.cache.LocalCache;
import org.noear.waad.cache.SecondCache;
import org.noear.waad.cache.memcached.MemCache;

/**
 * Created by noear on 2017/7/22.
 */
public class CacheUtil {
    public static boolean isUsingCache;

    ICacheService cache1 = new MemCache("text", 60, "127.0.0.0:8001",null,null);
    ICacheService cache2 = new LocalCache("xxxx", 60);

    //二级缓存
    ICacheService cachex = new SecondCache(cache1,cache2);


}
