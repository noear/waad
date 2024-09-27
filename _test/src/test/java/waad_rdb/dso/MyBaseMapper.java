package waad_rdb.dso;

import org.noear.waad.mapper.BaseMapper;

/**
 * @author noear 2023/7/23 created
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    default String testBaseExt() {
        System.out.println("test base ext");
        return "test base ext";
    }

    default String testBaseExt2(){
        return tableName();
    }
}
