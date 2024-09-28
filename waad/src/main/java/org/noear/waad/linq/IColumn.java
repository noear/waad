package org.noear.waad.linq;

import org.noear.waad.DbContext;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 列
 *
 * @author noear
 * @since 4.0
 */
public interface IColumn extends IExpr<IColumn> {
    /**
     * 列表
     * */
    String name();

    static String getNemes(DbContext db, IColumn... columns) {
        assert columns.length > 0;
        return Arrays.stream(columns).map(c -> c.name()).collect(Collectors.joining(","));
    }


    /**
     * = 操作符
     */
    default ICondition eq(Object val) {
        if (val instanceof IColumn) {
            return new IConditionLinq(this, " = ", (IColumn) val);
        }

        if (val == null) {
            return new IConditionLinq(this, " IS NULL ");
        } else {
            return new IConditionLinq(this, " = ?", val);
        }
    }

    /**
     * != 操作符
     */
    default ICondition neq(Object val) {
        if (val instanceof IColumn) {
            return new IConditionLinq(this, " != ", (IColumn) val);
        }

        if (val == null) {
            return new IConditionLinq(this, " IS NOT NULL ");
        } else {
            return new IConditionLinq(this, " != ?", val);
        }
    }

    /**
     * < 操作符
     */
    default ICondition lt(Object val) {
        if (val instanceof IColumn) {
            return new IConditionLinq(this, " < ", (IColumn) val);
        }

        return new IConditionLinq(this, " < ?", val);
    }

    /**
     * <= 操作符
     */
    default ICondition lte(Object val) {
        if (val instanceof IColumn) {
            return new IConditionLinq(this, " <= ", (IColumn) val);
        }

        return new IConditionLinq(this, " <= ?", val);
    }

    /**
     * > 操作符
     */
    default ICondition gt(Object val) {
        if (val instanceof IColumn) {
            return new IConditionLinq(this, " > ", (IColumn) val);
        }

        return new IConditionLinq(this, " > ?", val);
    }

    /**
     * >= 操作符
     */
    default ICondition gte(Object val) {
        if (val instanceof IColumn) {
            return new IConditionLinq(this, " >= ", (IColumn) val);
        }

        return new IConditionLinq(this, " >= ?", val);
    }

    /**
     * like 操作符
     */
    default ICondition like(String val) {
        return new IConditionLinq(this, " LIKE ?", val);
    }

    /**
     * not like 操作符
     */
    default ICondition nlike(String val) {
        return new IConditionLinq(this, " NOT LIKE ?", val);
    }

    /**
     * between 操作符
     */
    default ICondition between(Object start, Object end) {
        return new IConditionLinq(this, " BETWEEN ? AND ? ", start, end);
    }

    /**
     * not between 操作符
     */
    default ICondition nbetween(Object start, Object end) {
        return new IConditionLinq(this, " NOT BETWEEN ? AND ? ", start, end);
    }

    /**
     * in 操作符
     */
    default ICondition in(Iterable ary) {
        return new IConditionLinq(this, " IN (?...) ", ary);
    }

    /**
     * not in 操作符
     */
    default ICondition nin(Iterable ary) {
        return new IConditionLinq(this, " NOT IN (?...) ", ary);
    }
}
