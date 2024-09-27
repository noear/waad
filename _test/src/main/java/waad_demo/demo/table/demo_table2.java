package waad_demo.demo.table;

import org.noear.waad.DbContext;
import waad_demo.config.DbConfig;

import java.sql.SQLException;

/**
 * Created by noear on 2017/7/22.
 */
public class demo_table2 {

    static DbContext db = DbConfig.pc_user;

    public static void demo_expr1() throws SQLException {
          //连式处理::对不确定字段的插入
          db.table("test")
            .build(tb -> {
                tb.set("name", "xxx");

                if (1 == 2) {
                    tb.set("mobile", "xxxx");
                } else {
                    tb.set("icon", "xxxx");
                }
            }).insert();
    }
    public static void demo_expr1_2() throws SQLException {
        //新方案
        String icon = "xxxx";

        db.table("test")
                .set("name", "xxx")
                .setIf(1 == 2, "mobile", "xxxx")
                .setIf(icon != null, "icon", icon)
                .insert();
    }



    public static void demo_expr2() throws SQLException {
          //连式处理::对不确定的条件拼装
          db.table("test")
            .build(tb -> {
                tb.where("1=1");

                if (1 == 2) {
                    tb.and("mobile=?", "xxxx");
                } else {
                    tb.and("icon=?", "xxxx");
                }
            }).select("*");
    }

    public static void demo_expr2_2() throws SQLException {
        //新方案
        db.table("test")
                .where("1=1")
                .andIf(1 == 2, "mobile=?", "xxxx")
                .andIf(1 != 2, "icon=?", "xxxx")
                .select("*");
    }
}
