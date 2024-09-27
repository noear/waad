package org.noear.waad;

import org.noear.waad.tran.DbTran;
import org.noear.waad.tran.DbTranUtil;
import org.noear.waad.utils.fun.Act0Ex;
import org.noear.waad.utils.ThrowableUtils;

import java.sql.SQLException;

/**
 * @author noear 2020/12/27 created
 */

public class Trans {
    /**
     * 开始事务（如果当前有，则加入；否则新起事务）
     */
    public static DbTran tran(Act0Ex<Throwable> handler) throws SQLException {
        DbTran tran = DbTranUtil.current();

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
        DbTran tran = DbTranUtil.current();
        DbTranUtil.currentRemove();

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
                DbTranUtil.currentSet(tran);
            }
        }
    }
}