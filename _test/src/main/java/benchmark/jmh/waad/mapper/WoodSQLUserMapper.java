package benchmark.jmh.waad.mapper;


import benchmark.jmh.waad.model.WoodSQLSysUser;
import org.noear.waad.BaseMapper;
import org.noear.waad.annotation.Sql;
import org.noear.waad.xml.Namespace;

import java.util.List;

@Namespace("benchmark.jmh.waad.mapper")
public interface WoodSQLUserMapper extends BaseMapper<WoodSQLSysUser> {
    @Sql("select * from sys_user where id = ?")
    WoodSQLSysUser selectById(Integer id);

    @Sql("select * from sys_user where id = @{id}")
    WoodSQLSysUser selectTemplateById(Integer id);

    WoodSQLSysUser userSelect(Integer id);

    List<WoodSQLSysUser> queryPage(String code, int start, int end);
}