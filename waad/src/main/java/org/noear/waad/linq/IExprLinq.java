package org.noear.waad.linq;

import org.noear.waad.DbContext;
import org.noear.waad.util.StrUtils;

/**
 * @author noear
 * @since 4.0
 */
public class IExprLinq implements IExpr {
    private String symbol;
    private String asName;
    private Object[] args;

    public IExprLinq(String symbol, Object... args) {
        this.symbol = symbol;
        this.args = args;
    }

    private IExprLinq(String symbol, String asName, Object... args) {
        this.symbol = symbol;
        this.asName = asName;
        this.args = args;
    }

    @Override
    public String asName() {
        return asName;
    }

    @Override
    public IExpr as(String asName) {
        return new IExprLinq(symbol, asName, args);
    }

    @Override
    public String getCode(DbContext db) {
        StringBuilder buf = new StringBuilder(symbol.length() + 16);
        buf.append(" ").append(symbol);

        for (Object arg : args) {
            int idx = buf.indexOf("?");
            if (idx > -1) {
                if (arg instanceof IExpr) {
                    buf.replace(idx, idx + 1, ((IExpr) arg).getCode(db));
                } else {
                    buf.replace(idx, idx + 1, arg.toString());
                }
            }
        }

        if (StrUtils.isNotEmpty(asName)) {
            buf.append(" ").append(asName);
        }

        return buf.toString();
    }
}
