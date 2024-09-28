package org.noear.waad.linq;

import org.noear.waad.util.StrUtils;

/**
 * 表
 *
 * @author noear
 * @since 4.0
 */
public interface ITable<T> {
    /**
     * 避免命名冲突
     */
    ITableSpec __getTableSpec();

    /**
     * 所有列
     */
    default IColumn all() {
        if (StrUtils.isEmpty(__getTableSpec().asName())) {
            return new IColumnLinq(this, __getTableSpec().name() + ".*");
        } else {
            return new IColumnLinq(this, __getTableSpec().asName() + ".*");
        }
    }

    /**
     * 别名为
     */
    T as(String asName);
}