package org.noear.waad.link;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 列
 *
 * @author noear
 * @since 1.4
 */
public interface IColumn extends IExpr<IColumn> {
    /**
     * 列表
     * */
    String name();

    static String getCodes(IColumn... columns) {
        assert columns.length > 0;
        return Arrays.stream(columns).map(c -> c.getCode()).collect(Collectors.joining(","));
    }


    /**
     * = 操作符
     */
    default ICondition eq(Object val) {
        if (val == null) {
            return new IConditionLink(this, " IS NULL ");
        } else {
            return new IConditionLink(this, " = ?", val);
        }
    }

    /**
     * != 操作符
     */
    default ICondition neq(Object val) {
        if (val == null) {
            return new IConditionLink(this, " IS NOT NULL ");
        } else {
            return new IConditionLink(this, " != ?", val);
        }
    }

    /**
     * < 操作符
     */
    default ICondition lt(Object val) {
        return new IConditionLink(this, " < ?", val);
    }

    /**
     * <= 操作符
     */
    default ICondition lte(Object val) {
        return new IConditionLink(this, " <= ?", val);
    }

    /**
     * > 操作符
     */
    default ICondition gt(Object val) {
        return new IConditionLink(this, " > ?", val);
    }

    /**
     * >= 操作符
     */
    default ICondition gte(Object val) {
        return new IConditionLink(this, " >= ?", val);
    }

    /**
     * like 操作符
     */
    default ICondition like(String val) {
        return new IConditionLink(this, " LIKE ?", val);
    }

    /**
     * not like 操作符
     */
    default ICondition nlike(String val) {
        return new IConditionLink(this, " NOT LIKE ?", val);
    }

    /**
     * between 操作符
     */
    default ICondition between(Object start, Object end) {
        return new IConditionLink(this, " BETWEEN ? AND ? ", start, end);
    }

    /**
     * not between 操作符
     */
    default ICondition nbetween(Object start, Object end) {
        return new IConditionLink(this, " NOT BETWEEN ? AND ? ", start, end);
    }

    /**
     * in 操作符
     */
    default ICondition in(Iterable ary) {
        return new IConditionLink(this, " IN (?...) ", ary);
    }

    /**
     * not in 操作符
     */
    default ICondition nin(Iterable ary) {
        return new IConditionLink(this, " NOT IN (?...) ", ary);
    }
}
