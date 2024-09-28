package waad_rdb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.DbContext;
import org.noear.waad.core.DbType;
import webapp.model.AppxModel;
import waad_rdb.DbUtil;

import java.util.List;

public class WithTest {
    DbContext db = DbUtil.db;

    @Test
    public void test() throws Exception {
        if(db.metaData().getType()  == DbType.Oracle){
            return;
        }

        //
        // mysql 8.0 才支持
        //
        List<AppxModel> list  = db.table("#ag").innerJoin("#ax").on("ag.agroup_id = ax.agroup_id")
                .limit(10)
                .with("ax", db.table("appx").selectQ("*"))
                .with("ag", db.table("appx_agroup").where("agroup_id<?",10).selectQ("*"))
                .with("ah", "select * from appx_agroup where agroup_id<?", 10)
                .selectList(AppxModel.class, "ax.*");

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void test2() throws Exception {
        if(db.metaData().getType() == DbType.Oracle){
            return;
        }

        //
        // mysql 8.0 才支持
        //
        Object tmp = db.table("#ax")
                .orderBy("app_id DESC")
                .limit(2)
                .with("ax", db.table("appx").selectQ("*"))
                .selectMapList("ax.*");

        System.out.println(db.lastCommand.text());
    }

}
