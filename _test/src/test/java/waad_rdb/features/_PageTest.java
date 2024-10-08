package waad_rdb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.mapper.BaseMapper;
import org.noear.waad.DbContext;
import waad_rdb.DbUtil;
import webapp.model.AppxModel;

import java.util.List;

import static linq.APPX_LQ.APPX;

public class _PageTest {
    DbContext db2 = DbUtil.db;

    BaseMapper<AppxModel> mapper = db2.mapperBase(AppxModel.class);

    @Test
    public void test_top(){
        assert  mapper.selectById(22).app_id == 22;
        System.out.println(db2.lastCommand.text());
    }

    @Test
    public void test_page() throws Exception {

        List<AppxModel> list = mapper.selectList(0, 10, q->q.orderByAsc(APPX.APP_ID));
        assert list.size() == 10;
        assert list.get(0).app_id == 1;

        System.out.println(db2.lastCommand.text());
    }

    @Test
    public void test_page2() throws Exception{
        List<AppxModel> list =  mapper.selectList(1,10,q->q.orderByAsc(APPX.APP_ID));
        assert  list.size() == 10;
        assert list.get(0).app_id == 2;

        System.out.println(db2.lastCommand.text());
    }

    @Test
    public void test_page3() throws Exception {
        List<AppxModel> list = db2.table("appx a")
                .leftJoin("appx_agroup b").on("a.agroup_id = b.agroup_id")
                .orderBy("a.app_id ASC")
                .limit(1, 10)
                .selectList(AppxModel.class, "a.*,b.name agroup_name");

        assert list.size() == 10;
        assert list.get(0).app_id == 2;

        System.out.println(db2.lastCommand.text());
    }
}
