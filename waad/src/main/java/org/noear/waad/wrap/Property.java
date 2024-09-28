package org.noear.waad.wrap;

import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IColumnLinq;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Created by noear on 19-12-16.
 */
@FunctionalInterface
public interface Property<T, R> extends Function<T, R>, Serializable {
    /**
     * 转为例
     */
    default IColumn toColumn() {
        return new IColumnLinq(PropertyWrap.get(this).name);
    }
}