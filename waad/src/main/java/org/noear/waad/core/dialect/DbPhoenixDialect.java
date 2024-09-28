package org.noear.waad.core.dialect;

import org.noear.waad.core.DbDialectBase;
import org.noear.waad.core.DbType;

/**
 * Phoenix 数据库方言处理
 *
 * @author noear
 * @since 3.2
 * */
public class DbPhoenixDialect extends DbDialectBase {
    @Override
    public DbType dbType() {
        return DbType.Phoenix;
    }

    @Override
    public boolean supportsVariablePaging() {
        return true;
    }

    @Override
    public boolean supportsInsertGeneratedKey() {
        return false;
    }

    @Override
    public String tableFormat(String name) {
        return name;
    }

    @Override
    public String columnFormat(String name) {
        return name;
    }

    @Override
    public void insertCmd(StringBuilder sb, String table1) {
        sb.append("UPSERT INTO ").append(tableFormat(table1));
    }
}
