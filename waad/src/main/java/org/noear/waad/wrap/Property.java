package org.noear.waad.wrap;

import org.noear.waad.link.IColumn;
import org.noear.waad.link.IColumnImpl;

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
        return new IColumnImpl(PropertyWrap.get(this).name);
    }
}