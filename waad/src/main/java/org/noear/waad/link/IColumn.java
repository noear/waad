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
    String name();

    static String getCodes(IColumn... columns) {
        assert columns.length > 0;
        return Arrays.stream(columns).map(c -> c.getCode()).collect(Collectors.joining(","));
    }

    default ICondition eq(Object val) {
        if (val == null) {
            return new IConditionLink(this, " IS NULL ");
        } else {
            return new IConditionLink(this, " = ?", val);
        }
    }

    default ICondition neq(Object val){
        if (val == null) {
            return new IConditionLink(this, " IS NOT NULL ");
        } else {
            return new IConditionLink(this, " != ?", val);
        }
    }

    default ICondition lt(Object val){
        return new IConditionLink(this, " < ?", val);
    }

    default ICondition lte(Object val){
        return new IConditionLink(this, " <= ?", val);
    }

    default ICondition gt(Object val){
        return new IConditionLink(this, " > ?", val);
    }

    default ICondition gte(Object val){
        return new IConditionLink(this, " >= ?", val);
    }

    default ICondition lk(String val){
        return new IConditionLink(this, " LIKE ?", val);
    }

    default ICondition nlk(String val){
        return new IConditionLink(this, " NOT LIKE ?", val);
    }

    default ICondition btw(Object start, Object end){
        return new IConditionLink(this, " BETWEEN ? AND ? ", start, end);
    }

    default ICondition nbtw(Object start, Object end){
        return new IConditionLink(this, " NOT BETWEEN ? AND ? ", start, end);
    }

    default ICondition in(Iterable ary){
        return new IConditionLink(this, " IN (?...) ", ary);
    }

    default ICondition nin(Iterable ary){
        return new IConditionLink(this, " NOT IN (?...) ", ary);
    }
}
