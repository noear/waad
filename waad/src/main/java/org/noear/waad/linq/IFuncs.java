package org.noear.waad.linq;

/**
 * @author noear
 * @since 4.0
 */
public interface IFuncs {
    static IFunc max(IColumn column) {
        return new IFuncLinq(" MAX(?) ", column);
    }

    static IFunc avg(IColumn column) {
        return new IFuncLinq(" AVG(?) ", column);
    }

    static IFunc sum(IColumn column) {
        return new IFuncLinq(" SUM(?) ", column);
    }
}
