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
public class MapperWhereQ extends WhereBase<MapperWhereQ> {
    public MapperWhereQ(TableQuery query) {
        super(query);
    }

    public MapperWhereQ limit(int size) {
        _query.limit(size);
        return this;
    }

    public MapperWhereQ limit(int start, int size) {
        _query.limit(start, size);
        return this;
    }

    public MapperWhereQ fetchSize(int size) {
        _query.fetchSize(size);
        return this;
    }
}