package org.noear.waad.link;

import org.noear.waad.DbContext;

/**
 * 表申明
 *
 * @author noear
 * @since 4.0
 */
public interface ITableSpec {
    /**
     * 表名
     */
    String name();

    /**
     * 别名
     */
    String asName();

    /**
     * 代码
     */
    String getCode(DbContext db);
}
