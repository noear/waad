package benchmark.jmh.waad.model;

import lombok.Data;
import org.noear.waad.annotation.PrimaryKey;
import org.noear.waad.annotation.Table;

@Data
@Table("sys_user")
public class WaadSQLSysUser {
    @PrimaryKey
    private Integer id ;
    private String code ;
}
