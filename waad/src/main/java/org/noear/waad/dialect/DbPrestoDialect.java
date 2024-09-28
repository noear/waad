package org.noear.waad.dialect;

import org.noear.waad.wrap.DbType;

/**
 * @author noear 2021/10/21 created
 */
public class DbPrestoDialect extends DbDialectBase{
    @Override
    public DbType dbType() {
        return DbType.Presto;
    }

    @Override
    public boolean supportsVariablePaging() {
        return true;
    }

    @Override
    public boolean supportsInsertGeneratedKey() {
        return false;
    }
}
