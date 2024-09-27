package webapp.model;

import lombok.Data;
import org.noear.waad.annotation.Column;
import org.noear.waad.annotation.Exclude;
import org.noear.waad.annotation.PrimaryKey;
import org.noear.waad.annotation.Table;

import java.util.Date;

//和表名不相同，须注解表名
@Data
@Table("appx")
public class AppxModel {
    /** 应用ID */
    @PrimaryKey
    @Column("app_id")
    public Integer app_id;
    public String app_key;
    public String akey;
    public Integer ugroup_id;
    public Integer agroup_id;
    public String name;
    public String note;
    public Integer ar_is_setting;
    public Integer ar_is_examine;
    public Integer ar_examine_ver;
    /**  */
    public Date log_fulltime;

    @Exclude
    public String agroup_name;
}
