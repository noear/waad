package org.noear.waad.link;

/**
 * 条件
 *
 * @author noear
 * @since 4.0
 */
public interface ICondition {
    /**
     * 获取列
     */
    IColumn getColumn();

    /**
     * 获取描述
     */
    String getDescription();

    /**
     * 获取参数
     */
    Object[] getArgs();
}
