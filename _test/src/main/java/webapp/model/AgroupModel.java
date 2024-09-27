package webapp.model;

import lombok.Data;
import org.noear.waad.annotation.PrimaryKey;
import org.noear.waad.annotation.Table;

//和表名不相同，须注解表名
@Data
@Table("appx_agroup")
public class AgroupModel {
    @PrimaryKey
    public Integer agroup_id;
    public String name;
}
