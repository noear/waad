package org.noear.waad.core.dialect;

import org.noear.waad.DbContext;
import org.noear.waad.core.DbDialectBase;
import org.noear.waad.core.SQLBuilder;
import org.noear.waad.core.DbType;

/**
 * Postgre SQL 数据库方言处理
 *
 * @author noear
 * @since 3.2
 * */
public class DbPostgreSQLDialect extends DbDialectBase {
    @Override
    public DbType dbType() {
        return DbType.PostgreSQL;
    }

    @Override
    public boolean excludeFormat(String str) {
        return str.startsWith("\"") || str.indexOf(".") > 0;
    }

    @Override
    public String tableFormat(String tb) {
        return "\"" + tb + "\"";
    }

    @Override
    public String columnFormat(String col) {
        return "\"" + col + "\"";
    }

    @Override
    public boolean supportsVariablePaging() {
        return true;
    }

    @Override
    public void buildSelectRangeCode(DbContext ctx, String table1, SQLBuilder sqlB, StringBuilder orderBy, int start, int size) {
        sqlB.insert(0, "SELECT ");

        if (orderBy != null) {
            sqlB.append(orderBy);
        }

        if (supportsVariablePaging()) {
            sqlB.append(" LIMIT ? OFFSET ?");
            sqlB.paramS.add(size);
            sqlB.paramS.add(start);
        } else {
            sqlB.append(" LIMIT ")
                    .append(size)
                    .append(" OFFSET ")
                    .append(start);
        }
    }

    //top 和mysql一样
}
