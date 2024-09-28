package org.noear.waad.linq;

import org.noear.waad.DbContext;
import org.noear.waad.core.SQLBuilder;

/**
 * @author noear
 * @since 4.0
 */
public class IConditionLinq implements ICondition {
    private final IColumn column;
    private final String code;

    private Object[] args;
    private IColumn column2;

    public IConditionLinq(IColumn column, String code, Object... args) {
        this.column = column;
        this.code = code;
        this.args = args;
    }

    public IConditionLinq(IColumn column, String code, IColumn column2) {
        this.column = column;
        this.code = code;
        this.column2 = column2;
    }


    @Override
    public void write(DbContext db, SQLBuilder buf) {
        if (column2 == null) {
            buf.append(db.formater().formatColumn(column.getCode(db))).append(code, args);
        } else {
            buf.append(db.formater().formatColumn(column.getCode(db))).append(code).append(db.formater().formatColumn(column2.getCode(db)));
        }
    }
}
