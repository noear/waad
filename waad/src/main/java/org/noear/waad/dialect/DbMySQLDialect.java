package org.noear.waad.dialect;

import org.noear.waad.wrap.DbType;

/**
 * MySQL数据库方言处理
 *
 * @author noear
 * @since 3.2
 * */
public class DbMySQLDialect extends DbDialectBase{
    @Override
    public DbType dbType() {
        return DbType.MySQL;
    }

    @Override
    public boolean supportsVariablePaging() {
        return true;
    }
}
