package waad_rdb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.DbContext;
import waad_rdb.DbUtil;
import webapp.model.AppxModel;
import waad_rdb.features.linq.APPX_LQ;
import waad_rdb.features.linq.APPX_AGROUP_LQ;

import java.sql.SQLException;

import static waad_rdb.features.linq.APPX_AGROUP_LQ.APPX_AGROUP;
import static waad_rdb.features.linq.APPX_LQ.APPX;


public class TableJoinTest {
    DbContext db = DbUtil.db;

    @Test
    public void join_select() throws Exception {
        AppxModel m = db.table("appx a")
                .innerJoin("appx_agroup g").on("a.agroup_id = g.agroup_id")
                .where("a.app_id=?", 22)
                .selectItem(AppxModel.class, "a.*,g.name agroup_name");

        assert m.app_id == 22;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void join_select_link() throws Exception {
        AppxModel m = db.table(APPX)
                .innerJoin(APPX_AGROUP)
                .on(APPX.AGROUP_ID.eq(APPX_AGROUP.AGROUP_ID))
                .where(APPX.APP_ID.eq(22))
                .selectItem(AppxModel.class, APPX.all(), APPX_AGROUP.NAME.as("agroup_name"));

        assert m.app_id == 22;
        System.out.println(db.lastCommand.text());
    }

    @Test
    public void join_select_link_as() throws Exception {
        APPX_LQ a = APPX.as("a");
        APPX_AGROUP_LQ g = APPX_AGROUP.as("g");

        AppxModel m = db.table(a)
                .innerJoin(g)
                .on(a.AGROUP_ID.eq(g.AGROUP_ID))
                .where(a.APP_ID.eq(22) )
                .selectItem(AppxModel.class, a.all(), g.NAME.as("agroup_name"));

        //"a.*,g.name agroup_name",

        assert m.app_id == 22;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void join_select_link_as2() throws Exception {
        AppxModel m = db.table(APPX.as("a"))
                .innerJoin(APPX_AGROUP.as("g"))
                .on(APPX.as("a").AGROUP_ID.eq(APPX_AGROUP.as("g").AGROUP_ID))
                .where(APPX.as("a").APP_ID.eq(22))
                .selectItem(AppxModel.class, APPX.as("a").all(), APPX_AGROUP.as("g").NAME.as("agroup_name"));

        //"a.*,g.name agroup_name",

        assert m.app_id == 22;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void join_update() throws SQLException {
        db.table("#appx a, appx_agroup g")
                .set("a.note", "测试2")
                .where("a.agroup_id = g.agroup_id").and("a.app_id=?", 1)
                .update();

        System.out.print(db.lastCommand.text());
    }

    @Test
    public void join_update2() throws SQLException {
        db.table("appx a")
                .innerJoin("appx_agroup g").on("a.agroup_id = g.agroup_id")
                .set("a.note", "测试2")
                .where("a.app_id=?", 1)
                .update();

        System.out.print(db.lastCommand.text());
    }
}
