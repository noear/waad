package waad_adb.features;

import org.junit.jupiter.api.Test;
import org.noear.waad.DbContext;
import waad_adb.DbUtil;

public class _MetaTest {
    DbContext db = DbUtil.db;

    @Test
    public void test1() throws Exception{

        db.metaData().getTableAll().forEach(tw->{
            System.out.println("Table: "+tw.getName());
            tw.getColumns().forEach(cw->{
                System.out.print(cw.getName()+";");
            });
            System.out.println("");
        });

        System.out.println(db.metaData().getTableAll().size());

        assert  db.metaData().getTableAll().size() > 0;
    }
}
