package org.noear.waad.linq;

import org.noear.waad.DbContext;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 表达式
 *
 * @author noear
 * @since 4.0
 */
public interface IExpr<T extends IExpr> {
    static String getCodes(DbContext db, IExpr... columns) {
        assert columns.length > 0;
        return Arrays.stream(columns).map(c -> c.getCode(db)).collect(Collectors.joining(","));
    }

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
