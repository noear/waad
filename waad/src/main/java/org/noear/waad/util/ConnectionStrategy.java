package org.noear.waad.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * 连接策略
 *
 * @author noear
 * @since 3.2
 * @since 4.0
 * */
public class ConnectionStrategy {
    public Connection getConnection(DataSource ds) throws SQLException {
        return ds.getConnection();
    }
}
