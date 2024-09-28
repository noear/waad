package waad_rdb.features;

import org.noear.waad.model.DataRow;

import java.util.Map;

public class _TypeTest {
//    @Test
    public void test(){
        Map<String,Object> args = DataRow.create().set("date",20201010).getMap();
    }
}
