package webapp.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.waad.DbContext;
import org.noear.waad.annotation.Db;
import webapp.Config;
import webapp.model.AppxModel;


@Mapping("/java")
@Controller
public class JavaController {
    @Db
    DbContext db2;

    @Mapping("demo0/html")
    public ModelAndView demo0() throws Exception {
        ModelAndView mv = new ModelAndView("view.ftl");

        Object _map = demo3();
        mv.put("map", _map);

        return mv;
    }

    @Mapping("demo1/json")
    public Object demo1() throws Exception {
        //
        // select app_id from appx limit 1
        //
        return db2.table("appx")
                .limit(1)
                .selectValue("app_id");
    }

    @Mapping("demo2/json")
    public Object demo2() throws Exception {
        //
        // select * from appx where app_id = @{app_id} limit 1
        //
        int app_id = 48;

        return db2.table("appx")
                .where("app_id=?", app_id)
                .limit(1)
                .caching(Config.cache)
                .cacheTag("app_" + app_id)
                .selectItem(AppxModel.class, "*");
    }

    @Mapping("demo3/json")
    public Object demo3() throws Exception {
        //
        // select * from ${tb} where app_id = @{app_id} limit 1
        //
        int app_id = 48;
        String tb = "appx";

        Object tmp = db2.table(tb)
                .where("app_id=?", app_id)
                .limit(1)
                .selectMap("*");

        Config.cache.clear("test");

        return tmp;
    }

    @Mapping("demo4/json")
    public Object demo4() throws Exception {
        int app_id = 48;

        //
        // select * from appx where app_id>@{app_id} order by app_id asc limit 4
        //
        return db2.table("appx")
                .where("app_id>?",48)
                .orderBy("app_id ASC")
                .limit(4)
                .selectList(AppxModel.class, "*");
    }

    @Mapping("demo5/json")
    public Object demo5() throws Exception {
        //
        // select app_id from appx limit 4
        //
        return db2.table("appx")
                .limit(4)
                .selectArray("app_id");
    }
}
