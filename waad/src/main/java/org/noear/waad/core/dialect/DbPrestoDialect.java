package org.noear.waad.core.dialect;

import org.noear.waad.core.DbDialectBase;
import org.noear.waad.core.DbType;

/**
 * Presto 数据库方言处理
 *
 * @author noear
 * @since 2021/10/21
 */
public class DbPrestoDialect extends DbDialectBase {
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
