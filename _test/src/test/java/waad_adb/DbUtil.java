package waad_adb;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.waad.DbContext;
import org.noear.waad.DbDataSource;

import javax.sql.DataSource;

public class DbUtil {

    private final static DataSource dbClickHouseCfg() {
        DbDataSource ds = new DbDataSource("jdbc:clickhouse://localhost:8123/rock");

        ds.setDriverClassName("ru.yandex.clickhouse.ClickHouseDriver");

        return ds;
    }

    private final static DataSource dbPrestoCfg() {
        HikariDataSource ds = new HikariDataSource();

        ds.setSchema("rock");
        ds.setJdbcUrl("jdbc:presto://localhost:8123/rock?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=true");
        //ds.setUsername("root");
        //ds.setPassword("123456");
        ds.setDriverClassName("io.prestosql.jdbc.PrestoDriver");

        return ds;
    }


    public static DbContext getDb() {
        //
        DataSource source = dbClickHouseCfg();

        DbContext db = new DbContext(source).nameSet("rock");
        //WaadConfig.isUsingSchemaPrefix =true;
        //WaadConfig.isUsingUnderlineColumnName=true;
        db.onException((cmd, ex) -> {
            System.out.println(cmd.text);
        });

        db.onExecuteAft((cmd) -> {
            if (cmd.isBatch) {
                System.out.println(":::" + cmd.text + " --:batch");
            } else {
                System.out.println(":::" + cmd.text);
            }
        });

        db.initMetaData();
        return db;
    }

    public static DbContext db = getDb();
}
