package waad_rdb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.mapper.BaseMapper;
import org.noear.waad.DbContext;
import waad_rdb.DbUtil;
import webapp.model.AgroupModelEx;
import webapp.model.Appx2Model;


public class _PrivateTest {

    DbContext db = DbUtil.db;

    @Test
    public void test1() throws Exception {
        Appx2Model tmp = db.table("appx").where("app_id=?", 1).selectItem(Appx2Model.class, "*");

        assert tmp.getAppId() == 1;
        assert tmp.getAppKey() != null;
    }

    @Test
    public void test2() throws Exception {
        BaseMapper<Appx2Model> mapper = db.mapperBase(Appx2Model.class);

        Appx2Model tmp = mapper.selectById(1);

        assert tmp.getAppId() == 1;
    }

    @Test
    public void test3() throws Exception {
        BaseMapper<AgroupModelEx> mapper = db.mapperBase(AgroupModelEx.class);

        AgroupModelEx tmp = mapper.selectById(1);

        assert tmp.getAgroup_id() == 1;
        assert tmp.getTag() != null;
    }
}
