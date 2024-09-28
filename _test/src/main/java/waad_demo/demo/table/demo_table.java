package waad_demo.demo.table;

import org.noear.waad.model.DataRow;
import org.noear.waad.model.DataList;
import org.noear.waad.DbContext;
import org.noear.waad.TableQuery;
import waad_demo.config.DbConfig;
import waad_demo.demo.model.UserInfoModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by noear on 2017/7/22.
 */
public class demo_table {

    static DbContext db = DbConfig.pc_user;

    public static void demo_insert() throws SQLException{
        long row_id = db.table("test")
                .insert(DataRow.create().set("log_time", "$DATE(NOW())"));


        if (row_id == 0)
            return;
    }

    public static void demo_update() throws SQLException{

        //1
        db.table("test")
                .where("id IN (?...)", new int[] { 15,14,16})
                .update(DataRow.create().set("txt", "NOW()xx").set("num", 44));

        //2. 加别名
        db.table("test t")
                .where("t.id=?", 17)
                .update(DataRow.create().set("t.txt", "fff").set("num", 111));


        //1
        db.table("test")
                .where("id IN (?...)", db.table("user_info").where("user_id<?", 16).selectQ("user_id"))
                .update(DataRow.create().set("txt", "NOW()xx").set("num", 44));
    }

    public static void demo_delete() throws SQLException{
        db.table("test")
                .where("id=?", 12)
                .delete();
    }

    public static DataList demo_select() throws SQLException {
        return db.table("user_info")
                .where("user_id<?", 10)
                .selectDataList("user_id,name,sex");
    }

    public static void demo_select1() throws SQLException {
        db.table("user_info u")
                .innerJoin("user_ex e").on("u.useer_id = e.user_id")
                .where("u.user_id<?", 10)
                .selectDataList("u.user_id,u.name,u.sex");


        db.table("user_info u")
                .innerJoin("user_ex e").on("u.useer_id = e.user_id")
                .where("u.user_id<?", 10)
                .limit(10,20)
                .selectDataList("u.user_id,u.name,u.sex");

        db.table("user_info u")
                .innerJoin("user_ex e").on("u.useer_id = e.user_id")
                .where("u.user_id<?", 10)
                .groupBy("u.user_id")
                .limit(10,20)
                .selectDataList("u.user_id,COUNT(e.row_id)");
    }

    public static List<UserInfoModel> demo_select2() throws SQLException{
        List<UserInfoModel> list = db.table("user_info")
                .where("user_id<?", 10)
                .selectList(UserInfoModel.class, "user_id,name,sex");

        return list;
    }

    public static void demo_select_join() throws SQLException{
        DataList dt = db.table("test a")
                .innerJoin("user_info b").on("b.user_id=a.id")
                .where("a.id<?", 20)
                .limit(100)
                .selectDataList("a.*,b.name");


        int count = dt.size();
        if (count > 0)
            return;
    }

    public static void demo_select_complex() throws SQLException{
        DataList dt = db.table("test a")
                .innerJoin("user_info b").on("b.user_id=a.id")
                .where("a.id<15")
                .groupBy("a.num HAVING a.num>1")
                .orderBy("a.num DESC")
                .selectDataList("num,COUNT(b.user_id)");

        int count = dt.size();
        if (count > 0)
            return;
    }

    public static DataList demo_select_complex_pin() throws SQLException{
        DataList dbTable = doGetAllInvite(1, 20, "u", 10001, 0);

        return dbTable;
    }

    private static DataList doGetAllInvite(int pageIndex, int pageSize, String where, long whereVal, int _static)throws SQLException {
        DataList dl;
        DbContext db = DbConfig.pc_pool;

        int start = pageSize * (pageIndex - 1);

        //代码拼装
        TableQuery qr = db.table("$.invites").where("1=1");
        if (whereVal > 0) {
            if (where == "u")
                qr.and("(master_id=? OR user_id=?)", whereVal, whereVal);
            else
                qr.and(where + "=?", whereVal);
        }

        if (_static == 1)
            qr.and("(master_id > 10020 OR user_id > ？)", 10020);

        if (_static == 2)
            qr.and("(master_id <= 10020 AND user_id <= 10020)");

        qr.orderBy("invite_id DESC")
                .limit(start, pageSize);

        dl = qr.selectDataList("*");

        return dl;
    }

    public static DataList ddd() throws SQLException{
        DbContext db = DbConfig.pc_bcf;

        //1.找出我所有的资源
        List<Integer> rids = db.table("BCF_Resource r")
                .innerJoin("BCF_Resource_Linked rl").on("r.RSID=rl.RSID")
                .where("rl.LK_OBJT_ID=? AND rl.LK_OBJT=?", 12737, 7)
                .selectArray("r.RSID");

        //2.找出资源相关的组id
        List<Integer> pids = db.table("BCF_Resource_Linked rl")
                .where("rl.LK_OBJT=? AND rl.RSID IN (?...)", 2, rids)
                .selectArray("DISTINCT rl.LK_OBJT_ID");

        //3.找出相关组的诚意情
        return db.table("BCF_Group")
                .where("PGID IN (?...) AND Is_Disabled=0 AND Is_Visibled=1", pids)
                .orderBy("Order_Index")
                .selectDataList("*");
    }
}
