package waad_demo.demo_plus.storeProcedure;

import org.noear.waad.DbStoredProcedure;
import waad_demo.config.DbConfig;

/**
 * Created by noear on 2017/7/22.
 */
public class user_get extends DbStoredProcedure {
    public user_get() {
        super(DbConfig.test);

        lazyload(()->{
            call("user_get");
            set("_userID",userID);
        });
    }

    public long userID;
}
