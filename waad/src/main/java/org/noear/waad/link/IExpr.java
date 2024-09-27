package org.noear.waad.link;

/**
 * 表达式
 *
 * @author noear
 * @since 1.4
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
    String getCode();
}
