package waad_rdb.features;

import org.noear.waad.WaadConfig;

/**
 * @author noear 2021/4/2 created
 */
public class EventTest {
    public void demo(){
        WaadConfig.onExecuteAft((cmd)->{
            System.out.println("[Wood] " + cmd.toSqlString());
        });
    }
}
