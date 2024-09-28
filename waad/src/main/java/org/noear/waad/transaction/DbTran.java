package org.noear.waad.transaction;

import org.noear.waad.DbContext;
import org.noear.waad.WaadConfig;
import org.noear.waad.util.function.Act0Ex;
import org.noear.waad.util.ThrowableUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据事务：支持事务模式和单链接模式
 *
 * @author noear
 * @since 14-9-16
 */
public class DbTran {
    private final static ThreadLocal<DbTran> _tl_tran = new ThreadLocal<>();

    /**
     * 设置线程的当前事务
     */
    protected static void current(DbTran tran) {
        _tl_tran.set(tran);
    }

    /**
     * 获取线程的当前事务
     */
    public static DbTran current() {
        return _tl_tran.get();
    }

    /**
     * 移除线程的当前事务
     */
    public static void currentRemove() {
        _tl_tran.remove();
    }

    /////////////////////

    private final Map<DataSource, Connection> conMap = new HashMap<>();

    //是否自动提交
    private boolean _autoCommit = false;


    public Connection getConnection(DbContext db) throws SQLException {
        return getConnection(db.metaData().getDataSource());
    }

    public Connection getConnection(DataSource ds) throws SQLException {
        if (conMap.containsKey(ds)) {
            return conMap.get(ds);
        } else {
            Connection con = ds.getConnection();
            if (_autoCommit == false) {
                con.setAutoCommit(false);
            }

            conMap.putIfAbsent(ds, con);
            return con;
        }
    }


    /**
     * 是否自动提交
     */
    public DbTran autoCommit(boolean autoCommit) {
        _autoCommit = autoCommit;
        return this;
    }

    /*执行事务过程 = action(...) + excute() */
    public DbTran execute(Act0Ex<Throwable> handler) throws SQLException {
        //挂起之前的事务(备份)
        DbTran tranBak = DbTran.current();

        try {
            //开始事务
            DbTran.current(this);
            handler.run();

            if (!_autoCommit) {
                commit();
            }
        } catch (Throwable ex) {
            if (!_autoCommit) {
                rollback();
            }

            ex = ThrowableUtils.throwableUnwrap(ex);
            if (ex instanceof SQLException) {
                throw (SQLException) ex;
            } else if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }
        } finally {
            DbTran.currentRemove();

            close();

            if (tranBak != null) {
                //恢复之前的事务
                DbTran.current(tranBak);
            }
        }

        return this;
    }


    protected void rollback() throws SQLException {
        for (Map.Entry<DataSource, Connection> kv : conMap.entrySet()) {
            kv.getValue().rollback();
        }
    }

    protected void commit() throws SQLException {
        for (Map.Entry<DataSource, Connection> kv : conMap.entrySet()) {
            kv.getValue().commit();
        }
    }

    protected void close() throws SQLException {
        for (Map.Entry<DataSource, Connection> kv : conMap.entrySet()) {
            try {
                if (kv.getValue().isClosed() == false) {
                    kv.getValue().close();
                }
            } catch (Exception ex) {
                WaadConfig.globalEvents().runExceptionEvent(null, ex);
            }
        }
    }
}
