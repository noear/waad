package waad_rdb.dso.rocedure;

import org.noear.waad.DbContext;
import org.noear.waad.DbQueryProcedure;
import org.noear.waad.wrap.DbType;

public class appx_get extends DbQueryProcedure {
    public appx_get(DbContext context) {
        super(context);
        lazyload(()->{
            if(context.getType() == DbType.Oracle){
                sql("select * from \"$\".\"APPX\" where \"app_id\"=@{id}");
            }else{
                sql("select * from appx where app_id=@{id}");
            }


            set("id",app_id);
        });
    }

    public int app_id;
}
