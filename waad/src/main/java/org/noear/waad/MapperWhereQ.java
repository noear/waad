package org.noear.waad;

/**
 * 条件器。仅用于 BaseMapper，即用于单表操作
 *
 * @author noear
 * @since 19-12-11.
 * @since 4.0
 */
public class MapperWhereQ extends WhereBase<MapperWhereQ> {
    private TableQuery _query;
    public MapperWhereQ(TableQuery query) {
        super();

        _query = query;

        _context = _query._context;
        _builder = _query._builder;
        _hasGroup = _query._hasGroup;
    }

    @Override
    protected MapperWhereQ orderByDo(String code) {
        super.orderByDo(code);

        if (_query != null) {
            _query._orderBy = _orderBy;
        }

        return this;
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