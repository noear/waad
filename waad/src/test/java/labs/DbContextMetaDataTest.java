package labs;

import org.noear.waad.DbContextMetaData;

/**
 * @author noear 2024/9/5 created
 */
public class DbContextMetaDataTest {
    public static void main(String[] args) {
        try {
            new DbContextMetaData();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
