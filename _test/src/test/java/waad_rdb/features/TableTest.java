package waad_rdb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.DbContext;
import org.noear.waad.TableQuery;
import webapp.model.AppxD;
import waad_rdb.DbUtil;
import webapp.model.AppxModel;

import java.util.Map;

import static waad_rdb.features.link.APPX_LK.APPX;


public class TableTest {
    DbContext db = DbUtil.db;

    public void demo() throws Exception {
        Map<String, Object> map = db.table("appx").where("app_id=?", 1).selectMap("*");

        map.remove("app_id");

        TableQuery tq = db.table("appx_copy")
                .setMap(map);

        if (tq.where("app_id=?", 11).selectExists()) {
            //在同一个 tq 里 where 会被 update 复用
            tq.update();
        } else {
            tq.insert();
        }
    }

    public void demo2() throws Exception {
        db.table("appx_copy")
                .setInc("a", 1)
                .where("app_id=?", 11)
                .update();
    }

    public void demo3() throws Exception {
        db.table("appx_copy")
                .orderBy("grade when sort ='1' then 1 when sort ='2' then 2 when sort ='3' then 3 end")
                .selectMapList("a");
    }

    @Test
    public void test0() throws Exception {
        Map<String, Object> map = db.table("appx").where("app_id=?", 1).selectMap("*");

        map.remove("app_id");


        assert db.table("appx_copy")
                .setMap(map)
                .where("app_id=?", 11)
                .update() > 0;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void test0_link() throws Exception {
        Map<String, Object> map = db.table(APPX).where(APPX.APP_ID.eq(1)).selectMap(APPX.all());

        map.remove("app_id");


        assert db.table("appx_copy")
                .setMap(map)
                .where("app_id=?", 11)
                .update() > 0;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void test0_2() throws Exception {
        Map<String, Object> map = db.table("appx").where("app_id=?", 1).selectMap("*");

        map.put("app_id",11);

        assert db.table("appx_copy")
                .setMap(map)
                .updateBy("app_id") > 0;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void test02() throws Exception {
        Map<String, Object> map = db.table("appx").where("app_id=?", 1).selectMap("*");

        map.remove("app_id");

        assert db.table("appx_copy")
                .setMap(map)
                .where("app_id=?", 11).or("agroup_id IS NULL")
                .update() > 0;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void test1() throws Exception {
        assert db.table("appx")
                .where("app_id=?", 22)
                .selectItem(AppxModel.class, "*").app_id == 22;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void test1_link() throws Exception {
        assert db.table(APPX)
                .where(APPX.APP_ID.eq(22))
                .selectItem(AppxModel.class, APPX.all()).app_id == 22;

        System.out.println(db.lastCommand.text());
    }

    @Test
    public void test1_2() throws Exception {
        AppxD appxD = db.table("appx")
                .where("app_id=?", 22)
                .selectItem(AppxD.class, "*");

        assert appxD.app_id() == 22;

        System.out.println(db.lastCommand.text());

         appxD = DbContext.use("rock").table("appx")
                .where("app_id=?", 22)
                .selectItem(AppxD.class, "*");

        assert appxD.app_id() == 22;
    }

    @Test
    public void test1_2_link() throws Exception {
        AppxD appxD = db.table(APPX)
                .where(APPX.APP_ID.eq(22))
                .selectItem(AppxD.class, APPX.all());

        assert appxD.app_id() == 22;

        System.out.println(db.lastCommand.text());

        appxD = DbContext.use("rock").table(APPX)
                .where(APPX.APP_ID.eq(22))
                .selectItem(AppxD.class, APPX.all());

        assert appxD.app_id() == 22;
    }

    @Test
    public void test12() throws Exception {
        assert db.table("appx")
                .where("app_id IS NULL")
                .selectItem(AppxModel.class, "*").app_id == null;

        System.out.println(db.lastCommand.text());

        assert db.table("appx")
                .where("app_id IS NULL")
                .selectMap("*").size() == 0;
    }

    @Test
    public void test12_2() throws Exception {
        assert db.table("appx")
                .where("app_id IS NULL")
                .selectList(AppxModel.class, "*").size() == 0;

        System.out.println(db.lastCommand.text());

        assert db.table("appx")
                .where("app_id IS NULL")
                .selectMapList("*").size() == 0;
    }




    @Test
    public void test2() throws Exception {
        //删
        db.table("test").where("1=1").delete();

        //增
        db.table("test").set("v1", 1).set("id", 1).insert();
        db.table("test").set("v1", 2).set("id", 2).insert();
        db.table("test").set("v1", 3).set("id", 3).insert();

        assert db.table("test").selectCount() == 3;


        //改
        long id = 10;
        db.table("test").set("v1", 1).set("id", 10).insert();
        assert db.table("test")
                .set("v1", 10)
                .where("id=?", id)
                .update() == 1;

        //查
        assert db.table("test")
                .where("id=?", id)
                .selectVariate("v1")
                .longValue(0l) == 10;
    }

    @Test
    public void test3() throws Exception {
        assert db.table("test")
                .set("v1", 10)
                .where("id=?", 10)
                .update() == 1;
    }

    @Test
    public void test4() throws Exception {
        db.table("test")
                .set("v1", 10)
                .set("v2", null)
                .usingNull(true)
                .insert();
    }

    /**
     * 测试带group的分页查询
     * @throws Exception
     */
    @Test
    public void test5() throws Exception{

        long count = db.table("test").selectCount();
        if(count == 0){
            db.table("test").set("v1", 1).set("id", 1).insert();
            db.table("test").set("v1", 1).set("id", 2).set("v2","a").insert();
            db.table("test").set("v1", 2).set("id", 3).insert();
            db.table("test").set("v1", 3).set("id", 4).insert();
        }

        long count1 = db.table("test")
          .where("v1 !=?", 100)
          .groupBy("v1")
          .limit(100)
          .orderBy("id ASC")
          .selectCount();
        assert count1 > 0;

    }
}
