package waad_rdb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.mapper.BaseMapper;
import org.noear.waad.DbContext;
import org.noear.waad.model.Page;
import webapp.model.AppxModel;
import waad_rdb.DbUtil;

public class _PageTest2 {
    DbContext db2 = DbUtil.db;

    BaseMapper<AppxModel> mapper = db2.mapperBase(AppxModel.class);

    @Test
    public void test_top(){
        assert  mapper.selectById(22).app_id == 22;
        System.out.println(db2.lastCommand.text());
    }

    @Test
    public void test_page() throws Exception {

        Page<AppxModel> list = mapper.selectPage(0, 10, q->q.orderByAsc(AppxModel::getApp_id));
        assert list.getList().size() == 10;
        assert list.getList().get(0).app_id == 1;
        assert list.getTotal() > 10;
        assert list.getPages() > 0;
        assert list.getSize() == 10;

        System.out.println(db2.lastCommand.text());
    }

    @Test
    public void test_page2() throws Exception{
        Page<AppxModel> list =  mapper.selectPage(1,10, q->q.orderByAsc(AppxModel::getApp_id));
        assert  list.getList().size() == 10;
        assert list.getList().get(0).app_id == 2;
        assert list.getTotal() > 10;
        assert list.getPages() > 0;
        assert list.getSize() == 10;

        System.out.println(db2.lastCommand.text());
    }

    @Test
    public void test_page3() throws Exception {
        Page<AppxModel> list = db2.table("appx a")
                .leftJoin("appx_agroup b").on("a.agroup_id = b.agroup_id")
                .orderBy("a.app_id ASC")
                .limit(1, 10)
                .selectPage("a.*,b.name agroup_name", AppxModel.class);

        assert  list.getList().size() == 10;
        assert list.getList().get(0).app_id == 2;
        assert list.getTotal() > 10;
        assert list.getPages() > 0;
        assert list.getSize() == 10;

        System.out.println(db2.lastCommand.text());
    }

    @Test
    public void test_page4() throws Exception{
        Page<AppxModel> list = db2.table("appx")
                .where("app_id=?",2)
                .orderBy("app_id ASC")
                .selectPage("*", AppxModel.class);

        assert  list.getList().size() == 1;
        assert list.getList().get(0).app_id == 2;

        System.out.println(db2.lastCommand.text());
    }
}
