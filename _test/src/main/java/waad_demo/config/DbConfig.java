package waad_demo.config;

import org.noear.waad.DbContext;

/**
 * Created by noear on 2017/7/22.
 */
public class DbConfig {
    //数据库配置
    public static DbContext test = new DbContext("jdbc:mysql://test").name("test");

    public static DbContext pc_user  = new DbContext("jdbc:mysql://pc_bank");
    public static DbContext pc_bank  = new DbContext("jdbc:mysql://pc_bank");
    public static DbContext pc_live  = new DbContext("jdbc:mysql://pc_live");
    public static DbContext pc_base  = new DbContext("jdbc:mysql://pc_base");
    public static DbContext pc_trace = new DbContext("jdbc:mysql://pc_trace");
    public static DbContext pc_pool  = new DbContext("jdbc:mysql://pc_pool");
    public static DbContext pc_bcf   = new DbContext("jdbc:mysql://pc_bcf");

    //public static DbContext xxxx  = new DbContext("jdbc:mysql://db.zheq.org:3306/pc_user","root","1234");
}
