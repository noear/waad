package org.noear.waad.transaction;

import org.noear.waad.util.function.Act0Ex;
import org.noear.waad.util.ThrowableUtils;

import java.sql.SQLException;

/**
 * 事务控制器
 *
 * @author noear
 * @since 2020/12/27
 */
public class Trans {
    /**
     * 开始事务（如果当前有，则加入；否则新起事务）
     */
    public static DbTran tran(Act0Ex<Throwable> handler) throws SQLException {
        DbTran tran = DbTran.current();

        if (tran == null) {
            return new DbTran().execute(handler);
        } else {
            try {
                handler.run();
            } catch (Throwable ex) {
                ex = ThrowableUtils.throwableUnwrap(ex);
                if (ex instanceof SQLException) {
                    throw (SQLException) ex;
                } else if (ex instanceof RuntimeException) {
                    throw (RuntimeException) ex;
                } else {
                    throw new RuntimeException(ex);
                }
            }

            return tran;
        }
    }

    /**
     * 开始一个新的事务
     */
    public static DbTran tranNew(Act0Ex<Throwable> handler) throws SQLException {
        return new DbTran().execute(handler);
    }

    /**
     * 以非事务方式运行（如果当有事务，则挂起）
     */
    public static void tranNot(Act0Ex<Throwable> handler) throws SQLException {
        DbTran tran = DbTran.current();
        DbTran.currentRemove();

        try {
            handler.run();
        } catch (Throwable ex) {
            ex = ThrowableUtils.throwableUnwrap(ex);
            if (ex instanceof SQLException) {
                throw (SQLException) ex;
            } else if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }
        } finally {
            if (tran != null) {
                DbTran.current(tran);
            }
        }
    }
}