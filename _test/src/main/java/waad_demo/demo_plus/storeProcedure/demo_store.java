package waad_demo.demo_plus.storeProcedure;

import waad_demo.demo.model.UserInfoModel;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by noear on 2017/7/22.
 */
public class demo_store {
    public void demo() throws SQLException {
        user_get_list sp = new user_get_list();

        sp.userID = 12;
        sp.sex = 1;//男的

        sp.getList(UserInfoModel.class);
    }

    public void demo2() throws SQLException {
        user_get sp = new user_get();

        sp.userID = 12;

        sp.getItem(UserInfoModel.class);
    }

    public void demo3() throws SQLException {
        user_update sp = new user_update();

        sp.userID = 12;
        sp.city = "";
        sp.vipTime = new Date(1111111);

        sp.execute();
    }

}
