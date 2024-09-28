package org.noear.waad.link;

/**
 * @author noear
 * @since 4.0
 */
public interface FunsLink {
    static IColumn max(IColumn column) {
        return new IColumnFunc(" MAX(?) ", column);
    }

    static IColumn avg(IColumn column) {
        return new IColumnFunc(" AVG(?) ", column);
    }

    static IColumn sum(IColumn column) {
        return new IColumnFunc(" SUM(?) ", column);
    }
}
