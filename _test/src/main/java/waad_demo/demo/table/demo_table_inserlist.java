package waad_demo.demo.table;

import org.noear.waad.DbContext;
import org.noear.waad.model.DataRow;
import waad_demo.config.DbConfig;
import waad_demo.demo.model.UserInfoModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by noear on 2017/7/22.
 */
public class demo_table_inserlist {
    static DbContext db = DbConfig.pc_user;

    public void demo_insertlist() throws SQLException {
        List<UserInfoModel> list = new ArrayList<>();
        //list.add(new UserInfoModel()...);
        //list.add(new UserInfoModel()...);

        db.table("user").insertList(list, (d, m) -> {
            //m.setEntity(d);

            m.set("city_id", d.city_id);
            m.set("name", d.name);
            m.set("mobile", d.mobile);
            m.set("icon", d.icon);
            m.set("role", d.role);
        });
    }

    public void demo_insertlist2() throws SQLException {
        List<DataRow> items = new ArrayList<>();

        db.table("user").insertList(items);
    }
}
