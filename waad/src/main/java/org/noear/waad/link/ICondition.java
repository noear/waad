package org.noear.waad.link;

/**
 * @author noear 2024/9/27 created
 */
public interface ICondition {
    IColumn getColumn();
    String getDescription();
    Object[] getArgs();
}
