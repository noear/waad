package benchmark.jmh.waad;

import benchmark.jmh.BaseService;
import benchmark.jmh.DataSourceHelper;
import benchmark.jmh.waad.mapper.WaadSQLUserMapper;
import benchmark.jmh.waad.model.WaadSQLSysUser;
import benchmark.jmh.waad.model.WaadSysCustomer;
import org.noear.waad.mapper.BaseMapper;
import org.noear.waad.DbContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WaadService implements BaseService {

    WaadSQLUserMapper userMapper;
    BaseMapper<WaadSysCustomer> customerMapper;
    AtomicInteger idGen = new AtomicInteger(1000);

    DbContext db;

    public void init() {
        DataSource dataSource = DataSourceHelper.ins();

        this.db = new DbContext(dataSource).name("user");
        this.userMapper = db.mapper(WaadSQLUserMapper.class);
        this.customerMapper = db.mapperBase(WaadSysCustomer.class);
    }


    @Override
    public void addEntity() {
        WaadSQLSysUser sqlSysUser = new WaadSQLSysUser();
        sqlSysUser.setId(idGen.getAndIncrement());
        sqlSysUser.setCode("abc");

       Long tmp =  userMapper.insert(sqlSysUser, false);

        //System.out.println(tmp);
    }


    @Override
    public Object getEntity() {
        Object tmp=  userMapper.selectById(1);

        //System.out.println(tmp);
        return tmp;
    }



    @Override
    public void lambdaQuery() {
        List<WaadSQLSysUser> list = userMapper.selectList(wq -> wq.where("id=1"));
    }

    @Override
    public void executeJdbcSql() {
        WaadSQLSysUser user = userMapper.selectById(1);
    }

    public void executeJdbcSql2() throws SQLException{
        WaadSQLSysUser user = db.sql("select * from sys_user where id = ?",1)
                .getItem(WaadSQLSysUser.class);
    }

    @Override
    public void executeTemplateSql() {
        WaadSQLSysUser user = userMapper.selectTemplateById(1);
    }

    public void executeTemplateSql2() throws SQLException {
        WaadSQLSysUser user = db.call("select * from sys_user where id = @{id}")
                .set("id",1).getItem(WaadSQLSysUser.class);
    }

    @Override
    public void sqlFile() {
        WaadSQLSysUser user = userMapper.userSelect(1);
        //System.out.println(user);
    }

    @Override
    public void one2Many() {

    }


    @Override
    public void pageQuery() {
        List<WaadSQLSysUser> list = userMapper.queryPage("用户一", 1, 5);
        long count = userMapper.selectCount(wq -> wq.where("code=?", "用户一"));
        //System.out.println(list);
    }

    @Override
    public void complexMapping() {

    }
}
