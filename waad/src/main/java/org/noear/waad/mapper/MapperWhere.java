package org.noear.waad.mapper;

import org.noear.waad.TableQuery;
import org.noear.waad.WhereBase;

/**
 * 条件器。用于 BaseMapper，即用于单表操作
 *
 * @author noear
 * @since 19-12-11.
 * @since 4.0
 */
public class MapperWhere extends WhereBase<MapperWhere> {
    public MapperWhere(TableQuery query) {
        super(query);
    }

    public MapperWhere limit(int size) {
        _query.limit(size);
        return this;
    }

    public MapperWhere limit(int start, int size) {
        _query.limit(start, size);
        return this;
    }

    public MapperWhere fetchSize(int size) {
        _query.fetchSize(size);
        return this;
    }
}