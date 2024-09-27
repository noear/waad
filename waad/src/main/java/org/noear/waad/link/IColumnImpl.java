package org.noear.waad.link;

import org.noear.waad.lang.Nullable;
import org.noear.waad.utils.StringUtils;

/**
 * 连接字段
 *
 * @author noear
 * @since 1.4
 */
public class IColumnImpl implements IColumn {
    private final @Nullable ITable table;
    private final String name;
    private final String asName;

    public IColumnImpl(ITable table, String name) {
        this(table, name, null);
    }

    public IColumnImpl(ITable table, String name, String asName) {
        this.table = table;
        this.name = name;
        this.asName = asName;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String asName() {
        return asName;
    }

    @Override
    public IColumn as(String asName) {
        return new IColumnImpl(table, name, asName);
    }

    @Override
    public String getCode() {
        if (table == null || StringUtils.isEmpty(table.____getTableSpec().asName()) || name.contains(".")) {
            if (StringUtils.isEmpty(asName())) {
                return name;
            } else {
                return name + " as " + asName;
            }
        } else {
            if (StringUtils.isEmpty(asName())) {
                return table.____getTableSpec().asName() + "." + name;
            } else {
                return table.____getTableSpec().asName() + "." + name + " as " + asName;
            }
        }
    }

    @Override
    public String toString() {
        return getCode();
    }
}