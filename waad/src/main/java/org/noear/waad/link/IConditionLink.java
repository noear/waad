package org.noear.waad.link;

/**
 * @author noear
 * @since 3.0
 */
public class IConditionLink implements ICondition {
    private final IColumn column;
    private final String code;
    private final Object[] args;

    public IConditionLink(IColumn column, String code, Object... args) {
        this.column = column;
        this.code = code;
        this.args = args;
    }

    public IColumn getColumn() {
        return column;
    }

    @Override
    public String getDescription() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }
}
