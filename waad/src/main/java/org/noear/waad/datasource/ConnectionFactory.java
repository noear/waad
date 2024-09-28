package org.noear.waad.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author noear 2024/9/28 created
 */
public interface ConnectionFactory {
    Connection getConnection(DataSource ds) throws SQLException;
}
