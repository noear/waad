package waad_adb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.DbContext;
import waad_adb.DbUtil;

public class _MetaTest {
    DbContext db = DbUtil.db;

    @Test
    public void test1() throws Exception{

        db.getMetaData().getTableAll().forEach(tw->{
            System.out.println("Table: "+tw.getName());
            tw.getColumns().forEach(cw->{
                System.out.print(cw.getName()+";");
            });
            System.out.println("");
        });

        System.out.println(db.getMetaData().getTableAll().size());

        assert  db.getMetaData().getTableAll().size() > 0;
    }
}
