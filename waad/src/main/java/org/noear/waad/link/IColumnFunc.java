package org.noear.waad.link;

import org.noear.waad.DbContext;
import org.noear.waad.util.StrUtils;

/**
 * @author noear
 * @since 4.0
 */
public class IColumnFunc implements IColumn {
    private String code;
    private String asName;
    private IColumn[] columns;

    public IColumnFunc(String code, IColumn... columns) {
        this.code = code;
        this.columns = columns;
    }

    private IColumnFunc(String code, String asName, IColumn... columns) {
        this.code = code;
        this.asName = asName;
        this.columns = columns;
    }

    @Override
    public String name() {
        StringBuilder buf = new StringBuilder(code);
        for (IColumn column : columns) {
            int idx = buf.indexOf("?");
            if (idx > -1) {
                buf.replace(idx, idx + 1, column.getCode(null));
            }
        }

        return buf.toString();
    }

    @Override
    public String asName() {
        return asName;
    }

    @Override
    public IColumn as(String asName) {
        return new IColumnFunc(code, asName, columns);
    }

    @Override
    public String getCode(DbContext db) {
        StringBuilder buf = new StringBuilder(code);
        for (IColumn column : columns) {
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
