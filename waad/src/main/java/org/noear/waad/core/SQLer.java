package org.noear.waad.core;

import org.noear.waad.*;
import org.noear.waad.model.DataList;
import org.noear.waad.model.DataRow;
import org.noear.waad.model.DataReaderForDataRow;

import java.sql.*;


/**
 * Created by noear on 14-6-12.
 * 数据库执行器
 */
public class SQLer {
    private final Command cmd;
    private ResultSet rset;
    private PreparedStatement stmt;
    private Connection conn;

    public void tryClose() {
        try {
            if (rset != null) {
                rset.close();
                rset = null;
            }
        } catch (Exception ex) {
            cmd.context.events().runExceptionEvent(null, ex);
        }

        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch (Exception ex) {
            cmd.context.events().runExceptionEvent(null, ex);
        }

        try {
            if (conn != null) {
                if (conn.getAutoCommit()) {
                    conn.close();
                }
                conn = null;
            }
        } catch (Exception ex) {
            cmd.context.events().runExceptionEvent(null, ex);
        }
    }

    public SQLer(Command cmd) {
        this.cmd = cmd;
    }

    private Object getObject(String key) throws SQLException {
        return cmd.context.getDialect().preChange(rset.getObject(key));
    }

    private Object getObject(int idx) throws SQLException {
        return cmd.context.getDialect().preChange(rset.getObject(idx));
    }

    public Variate getVariate() throws SQLException {
        if (cmd.context.isCompilationMode()) {
            return null;
        }

        try {
            rset = query(false, 0);

            if (rset != null && rset.next())
                return new Variate(null, getObject(1));
            else
                return null;//new Variate(null,null);
        } catch (SQLException ex) {
            cmd.context.events().runExceptionEvent(cmd, ex);
            throw ex;
        } finally {
            tryClose();
        }
    }

    public DataRow getRow() throws SQLException {
        if (cmd.context.isCompilationMode()) {
            return null;
        }

        try {
            DataRow row = DataRow.create();

            rset = query(false, 0);
            ResultSetMetaData meta = rset.getMetaData();

            if (rset != null && rset.next()) {

                int len = meta.getColumnCount();

                for (int i = 1; i <= len; i++) {
                    row.set(meta.getColumnLabel(i), getObject(i));
                }
            }

            if (row.size() > 0)
                return row;
            else
                return null;

        } catch (SQLException ex) {
            cmd.context.events().runExceptionEvent(cmd, ex);
            throw ex;
        } finally {
            tryClose();
        }
    }

    public DataList getTable() throws SQLException {
        if (cmd.context.isCompilationMode()) {
            return null;
        }

        try {
            DataList table = DataList.create();

            rset = query(false, 0);
            ResultSetMetaData meta = rset.getMetaData();

            while (rset != null && rset.next()) {
                DataRow row = DataRow.create();
                int len = meta.getColumnCount();

                for (int i = 1; i <= len; i++) {
                    row.set(meta.getColumnLabel(i), getObject(i));
                }

                table.add(row);
            }

            if (table.size() > 0)
                return table;
            else
                return null;

        } catch (SQLException ex) {
            cmd.context.events().runExceptionEvent(cmd, ex);
            throw ex;
        } finally {
            tryClose();
        }
    }

    public DataReaderForDataRow getReader(int fetchSize) throws SQLException {
        if (cmd.context.isCompilationMode()) {
            return null;
        }

        try {
            rset = query(true, fetchSize);
            return new DataReaderForDataRow(this, cmd, rset);
        } catch (SQLException ex) {
            cmd.context.events().runExceptionEvent(cmd, ex);
            tryClose();
            throw ex;
        }
    }

    //执行
    public int execute() throws SQLException {
        if (cmd.context.isCompilationMode()) {
            return 0;
        }

        try {
            if (false == buildCMD(false, false, 0)) {
                return -1;
            }

            int rst = stmt.executeUpdate();

            cmd.affectRow = new long[]{rst};

            return rst;

        } catch (SQLException ex) {
            cmd.context.events().runExceptionEvent(cmd, ex);
            throw ex;
        } finally {
            //*.监听
            cmd.context.events().runExecuteAftEvent(cmd);

            tryClose();
        }
    }

    //批量执行
    public int[] executeBatch() throws SQLException {
        if (cmd.context.isCompilationMode()) {
            return null;
        }

        try {
            if (false == buildCMD0(false, false, 0)) {
                return null;
            }

            for (Object data : cmd.paramS) {
                int idx = 1;
                Object[] ary = (Object[]) data;
                //2.设置参数值
                for (Object v : ary) {
                    WaadConfig.typeConverter.filling(stmt, idx, v);
                    idx++;
                }
                stmt.addBatch();
            }


            int[] rst = stmt.executeBatch();

            cmd.affectRow = new long[rst.length];
            for (int i = 0; i < rst.length; i++) {
                cmd.affectRow[i] = rst[i];
            }

            return rst;

        } catch (SQLException ex) {
            cmd.context.events().runExceptionEvent(cmd, ex);
            throw ex;
        } finally {
            //*.监听
            cmd.context.events().runExecuteAftEvent(cmd);
            tryClose();
        }
    }

    //插入
    public long insert() throws SQLException {
        if (cmd.context.isCompilationMode()) {
            return 0;
        }

        try {
            if (false == buildCMD(true, false, 0)) {
                return -1;
            }

            stmt.executeUpdate();

            if (cmd.context.getDialect().supportsInsertGeneratedKey()) {
                try {
                    rset = stmt.getGeneratedKeys(); //乎略错误
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            //这里，是与.execute()区别的地方
            if (rset != null && rset.next()) {
                Object tmp = getObject(1);
                if (tmp instanceof Number) {
                    long l = ((Number) tmp).longValue();
                    cmd.affectRow = new long[]{l};
                    return l;
                }
            }

            return 0l;
        } catch (SQLException ex) {
            cmd.context.events().runExceptionEvent(cmd, ex);
            throw ex;
        } finally {
            //*.监听
            cmd.context.events().runExecuteAftEvent(cmd);
            tryClose();
        }
    }

    //查询
    private ResultSet query(boolean isStream, int fetchSize) throws SQLException {
        if (false == buildCMD(false, isStream, fetchSize)) {
            return null;
        }

        try {
            //3.执行
            return stmt.executeQuery();
        } finally {
            //*.监听
            cmd.context.events().runExecuteAftEvent(cmd);
        }
    }

    private boolean buildCMD(boolean isInsert, boolean isStream, int fetchSize) throws SQLException {
        if (buildCMD0(isInsert, isStream, fetchSize) == false) {
            return false;
        }

        int idx = 1;
        //2.设置参数值
        for (Object v : cmd.paramS) {
            WaadConfig.typeConverter.filling(stmt, idx, v);
            idx++;
        }

        return true;
    }

    private boolean buildCMD0(boolean isInsert, boolean isStream, int fetchSize) throws SQLException {
        //*.监听
        if (cmd.context.events().runExecuteBefEvent(cmd) == false) {
            return false;
        }

        //1.构建连接和命令(外部的c不能给conn)
        Connection c;
        if (cmd.tran == null) {
            c = conn = cmd.context.getConnection();
        } else {
            c = cmd.tran.getConnection(cmd.context); //事务时，conn 须为 null
        }

        if (cmd.text.indexOf("{call") >= 0)
            stmt = c.prepareCall(cmd.fullText());
        else {
            if (isStream) {
                stmt = c.prepareStatement(cmd.fullText(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

                if (fetchSize > 0) {
                    stmt.setFetchSize(fetchSize);
                } else {
                    stmt.setFetchSize(Integer.MIN_VALUE);
                }
            } else {
                if (isInsert && cmd.context.getDialect().supportsInsertGeneratedKey())
                    stmt = c.prepareStatement(cmd.fullText(), Statement.RETURN_GENERATED_KEYS);
                else
                    stmt = c.prepareStatement(cmd.fullText());
            }
        }

        cmd.context.events().runExecuteStmEvent(cmd, stmt);

        return true;
    }
}
