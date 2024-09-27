package benchmark.jmh.waad.mapper;


import benchmark.jmh.waad.model.WaadSQLSysUser;
import org.noear.waad.mapper.BaseMapper;
import org.noear.waad.annotation.Sql;
import org.noear.waad.xml.Namespace;

import java.util.List;

@Namespace("benchmark.jmh.waad.mapper")
public interface WaadSQLUserMapper extends BaseMapper<WaadSQLSysUser> {
    @Sql("select * from sys_user where id = ?")
    WaadSQLSysUser selectById(Integer id);

    @Sql("select * from sys_user where id = @{id}")
    WaadSQLSysUser selectTemplateById(Integer id);

    WaadSQLSysUser userSelect(Integer id);

    List<WaadSQLSysUser> queryPage(String code, int start, int end);
}