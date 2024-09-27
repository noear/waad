package webapp;

import org.noear.solon.Solon;
import org.noear.waad.WaadConfig;

/**
 * @author noear 2021/1/23 created
 */
public class DemoApp {
    public static void main(String[] args){
        Solon.start(DemoApp.class, args,x->{
            WaadConfig.isUsingUnderlineColumnName = false;
        });
    }
}
