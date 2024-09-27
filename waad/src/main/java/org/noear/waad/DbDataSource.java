package org.noear.waad;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * 数据据源
 * */
public class DbDataSource implements DataSource {
    protected PrintWriter logWriter;
    protected String url;
    protected String username;
    protected String password;

    public DbDataSource(String url) {
        this.logWriter = new PrintWriter(System.out);
        this.url = url;
    }

    public DbDataSource(String url, String username,String password) {
        this.logWriter = new PrintWriter(System.out);
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public void setUrl(String url){
        this.url = url;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setDriverClassName(String driverClass) {
        try {
            Class.forName(driverClass);
        }catch (ClassNotFoundException ex){
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (username == null) {
            return DriverManager.getConnection(url);
        }
        else {
            return DriverManager.getConnection(url, username, password);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbDataSource that = (DbDataSource) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, username, password);
    }
}
