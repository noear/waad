package org.noear.waad.link;

/**
 * 表申明
 *
 * @author noear
 * @since 4.0
 */
public interface ITableSpec extends IExpr<ITableSpec> {
    /**
     * 表名
     */
    String name();
}
