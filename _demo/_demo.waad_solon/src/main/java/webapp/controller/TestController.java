package webapp.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Singleton;
import org.noear.waad.BaseMapper;
import org.noear.waad.DbContext;
import org.noear.waad.annotation.Db;
import webapp.dso.SqlMapper;
import webapp.model.AppxModel;

@Mapping("/test")
@Controller
public class TestController {
    @Db
    DbContext db2;

    @Mapping("demo1")
    public Object test(String sql) throws Exception {
        //
        // mysql 8.0 才支持
        //
        Object tmp = db2.table("ag").innerJoin("ax").on("ag.agroup_id = ax.agroup_id")
                .limit(10)
                .with("ax", db2.table("appx").selectQ("*"))
                .with("ag", db2.table("appx_agroup").where("agroup_id < 10").selectQ("*"))
                .with("ah", "select * from appx_agroup where agroup_id<?", 10)
                .selectMapList("ax.*");

        if (sql == null) {
            return tmp;
        } else {
            return db2.lastCommand.text();
        }
    }

    @Mapping("demo2")
    public Object test2(String sql) throws Exception {
        //
        // mysql 8.0 才支持
        //
        Object tmp = db2.table("ax")
                .orderByDesc("app_id")
                .limit(2)
                .with("ax", db2.table("appx").selectQ("*"))
                .selectMapList("ax.*");

        if (sql == null) {
            return tmp;
        } else {
            return db2.lastCommand.text();
        }
    }

    @Mapping("demo3")
    public Object test3(String sql) throws Exception {
        BaseMapper<AppxModel> mapper = db2.mapperBase(AppxModel.class);
        Object tmp = mapper.selectById(48);

        Object tmp2 = mapper.selectItem(m -> m.where("app_id=?", 12));

        Object tmp3 = mapper.selectItem(m -> m.where("app_id=?", 21));

        if (sql == null) {
            return tmp;
        } else {
            return db2.lastCommand.text();
        }
    }

    @Mapping("demo4")
    public Object test4(String sql) throws Exception {
        SqlMapper mapper = db2.mapper(SqlMapper.class);

        Object tmp = mapper.selectById(48);

        Object tmp2 = mapper.selectItem(m -> m.where("app_id=?", 12));

        Object tmp3 = mapper.selectItem(m -> m.where("app_id=?", 21));

        if (sql == null) {
            return tmp;
        } else {
            return db2.lastCommand.text();
        }
    }
}
