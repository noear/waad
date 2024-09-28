package org.noear.waad.linq;

import org.noear.waad.DbContext;
import org.noear.waad.util.StrUtils;

/**
 * @author noear
 * @since 4.0
 */
public class IFuncLinq implements IFunc {
    private String code;
    private String asName;
    private IExpr[] columns;

    public IFuncLinq(String code, IExpr... columns) {
        this.code = code;
        this.columns = columns;
    }

    private IFuncLinq(String code, String asName, IExpr... columns) {
        this.code = code;
        this.asName = asName;
        this.columns = columns;
    }

    @Override
    public String asName() {
        return asName;
    }

    @Override
    public IFunc as(String asName) {
        return new IFuncLinq(code, asName, columns);
    }

    @Override
    public String getCode(DbContext db) {
        StringBuilder buf = new StringBuilder(code);
        for (IExpr column : columns) {
            int idx = buf.indexOf("?");
            if (idx > -1) {
                buf.replace(idx, idx + 1, column.getCode(db));
            }
        }

        if (StrUtils.isNotEmpty(asName)) {
            buf.append(" ").append(asName);
        }

        return buf.toString();
    }
}
