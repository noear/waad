package org.noear.waad.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SimpleConnectionFactory implements ConnectionFactory{
    public Connection getConnection(DataSource ds) throws SQLException {
        return ds.getConnection();
    }


}
