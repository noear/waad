package waad_rdb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.model.DataRow;
import org.noear.waad.DbContext;
import org.noear.waad.cache.ICacheServiceEx;
import org.noear.waad.cache.LocalCache;
import waad_rdb.DbUtil;
import webapp.model.AppxModel;
import webapp.model.AppxModel2;

public class CacheTest {
    DbContext db2 = DbUtil.db;
    ICacheServiceEx cache = new LocalCache();

    @Test
    public void test1() throws Exception {
        AppxModel tmp = db2.table("appx")
                .where("app_id=?", 23)
                .caching(cache)
                .cacheTag("app_23")
                .selectItem(AppxModel.class, "*");

        System.out.println("tmp.app_id = " + tmp.app_id);
        assert tmp.app_id == 23;

        cache.tags().update("app_23", DataRow.class, (DataRow di) -> {
            AppxModel m = di.toEntity(AppxModel.class);
            assert m.app_id == 23;
            return di;
        });
    }

    @Test
    public void test2() throws Exception {
        AppxModel tmp = db2.table("appx")
                .where("app_id=?", 23)
                .caching(cache)
                .select("*")
                .getItem(AppxModel.class, (uc, m) -> {
                    uc.cacheTag("app_" + m.app_id);
                });

        System.out.println("tmp.app_id = " + tmp.app_id);
        assert tmp.app_id == 23;

        cache.tags().update("app_23",  DataRow.class, (DataRow di) -> {
            AppxModel m = di.toEntity(AppxModel.class);
            System.out.println("tmp.app_id = " + tmp.app_id);
            assert m.app_id == 23;
            return di;
        });
    }

    @Test
    public void test3() throws Exception {
        AppxModel2 tmp = db2.table("appx")
                .where("app_id=?", 23)
                .caching(cache)
                .select("*")
                .getItem(AppxModel2.class, (uc, m) -> {
                    uc.cacheTag("app_" + m.app_id);
                });

        System.out.println("tmp.app_id = " + tmp.app_id);
        assert tmp.app_id == 23;


        tmp = db2.table("appx")
                .where("app_id=?", 23)
                .caching(cache)
                .select("*")
                .getItem(AppxModel2.class, (uc, m) -> {
                    uc.cacheTag("app_" + m.app_id);
                });

        System.out.println("tmp.app_id = " + tmp.app_id);
        assert tmp.app_id == 23;


        cache.tags().update("app_23", DataRow.class,(DataRow di) -> {
            AppxModel2 m = di.toEntity(AppxModel2.class);
            System.out.println("tmp.app_id = " + m.app_id);
            assert m.app_id == 23;
            return di;
        });
    }
}
