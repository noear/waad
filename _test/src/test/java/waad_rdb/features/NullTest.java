package waad_rdb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.BaseMapper;
import org.noear.waad.DbContext;
import org.noear.waad.WaadConfig;
import webapp.model.AppxModel;
import waad_rdb.DbUtil;

import java.sql.SQLException;

/**
 * @author noear 2021/8/27 created
 */
public class NullTest {
    DbContext db2 = DbUtil.db;
    BaseMapper<AppxModel> mapper = db2.mapperBase(AppxModel.class);

    @Test
    public void test() throws SQLException {
        AppxModel temp = db2.table("appx")
                .where("app_id=?", Integer.MAX_VALUE)
                .selectItem("*", AppxModel.class);

        assert temp != null;

        WaadConfig.isSelectItemEmptyAsNull = true;

        AppxModel temp2 = db2.table("appx")
                .where("app_id=?", Integer.MAX_VALUE)
                .selectItem("*", AppxModel.class);

        WaadConfig.isSelectItemEmptyAsNull = false;

        assert temp2 == null;
    }


    @Test
    public void test2() throws SQLException {
        AppxModel temp = mapper.selectById(Integer.MAX_VALUE);

        assert temp != null;

        WaadConfig.isSelectItemEmptyAsNull = true;

        AppxModel temp2 = mapper.selectById(Integer.MAX_VALUE);

        WaadConfig.isSelectItemEmptyAsNull = false;

        assert temp2 == null;
    }

}
