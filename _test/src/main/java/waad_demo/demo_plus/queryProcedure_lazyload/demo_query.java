package waad_demo.demo_plus.queryProcedure_lazyload;

import waad_demo.demo.model.UserInfoModel;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by noear on 2017/7/22.
 */
public class demo_query {

    public void demo() throws SQLException {
        user_get_list2 sp = new user_get_list2();

        sp.userID = 12;
        sp.sex = 1;//男的

        sp.getList(UserInfoModel.class);
    }

    public void demo2() throws SQLException {
        user_get2 sp = new user_get2();

        sp.userID = 12;

        sp.getItem(UserInfoModel.class);
    }

    public void demo3() throws SQLException {
        user_update2 sp = new user_update2();

        sp.userID = 12;
        sp.city = "";
        sp.vipTime = new Date(1111111);

        sp.execute();
    }
}
