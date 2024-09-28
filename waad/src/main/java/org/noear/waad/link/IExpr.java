package org.noear.waad.link;

import org.noear.waad.DbContext;

/**
 * 表达式
 *
 * @author noear
 * @since 4.0
 */
public interface IExpr<T> {
    /**
     * 别名
     */
    String asName();

    /**
     * 附加别名
     */
    T as(String asName);

    /**
     * 代码
     */
    String getCode(DbContext db);
}
