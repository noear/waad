package webapp.model;

import lombok.Data;

//和表名不相同，须注解表名
@Data
public class AppxModel2{
    public int app_id;
    public String app_key;
    public String akey;

    public int agroup_id;
    public int ugroup_id;

    public String name;
    public String note;

    private int _ar_is_examine;
    private int _ar_examine_ver;
}
