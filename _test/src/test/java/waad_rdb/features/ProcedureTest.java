package waad_rdb.features;

import org.noear.waad.DbContext;
import waad_rdb.DbUtil;
import waad_rdb.dso.rocedure.appx_get_byid;
import webapp.model.AppxModel;

public class ProcedureTest {
    DbContext db2 = DbUtil.db;

    public void test22() throws Exception {
        appx_get_byid sp = new appx_get_byid(db2);
        sp.app_id = 22;

        assert  sp.getItem(AppxModel.class).app_id == 22;
    }
}
