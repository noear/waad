package org.noear.waad.link;

import org.noear.waad.DbContext;
import org.noear.waad.annotation.Nullable;
import org.noear.waad.util.StrUtils;

/**
 * 连接字段
 *
 * @author noear
 * @since 4.0
 */
public class IColumnLink implements IColumn {
    private final @Nullable ITable table;
    private final String name;
    private final String asName;

    public IColumnLink(String name) {
        this(null, name);
    }

    public IColumnLink(ITable table, String name) {
        this(table, name, null);
    }

    public IColumnLink(ITable table, String name, String asName) {
        this.table = table;
        this.name = name;
        this.asName = asName;
    }

    /**
     * 列名
     * */
    @Override
    public String name() {
        return name;
    }

    /**
     * 别名
     * */
    @Override
    public String asName() {
        return asName;
    }

    @Override
    public IColumn as(String asName) {
        return new IColumnLink(table, name, asName);
    }

    @Override
    public String getCode(DbContext db) {
        if (table == null || StrUtils.isEmpty(table.__getTableSpec().asName()) || name.contains(".")) {
            if (StrUtils.isEmpty(asName())) {
                return name;
            } else {
                return name + " as " + asName;
            }
        } else {
            if (StrUtils.isEmpty(asName())) {
                return table.__getTableSpec().asName() + "." + name;
            } else {
                return table.__getTableSpec().asName() + "." + name + " as " + asName;
            }
        }
    }

    @Override
    public String toString() {
        return getCode(null);
    }
}