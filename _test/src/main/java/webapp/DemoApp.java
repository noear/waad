package webapp;

import org.noear.solon.Solon;
import org.noear.waad.WoodConfig;

/**
 * @author noear 2021/1/23 created
 */
public class DemoApp {
    public static void main(String[] args){
        Solon.start(DemoApp.class, args,x->{
            WoodConfig.isUsingUnderlineColumnName = false;
        });
    }
}
