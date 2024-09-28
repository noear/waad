package org.noear.waad;

import org.noear.waad.wrap.Property;

/**
 * Created by noear on 19-12-11.
 *
 * 仅用于BaseMapper，即用于单表操作
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

    @Override
    public MapperWhereQ orderBy(String code) {
        super.orderBy(code);

        if (_query != null) {
            _query._orderBy = _orderBy;
        }

        return this;
    }


    public <C> MapperWhereQ whereEq(Property<C, ?> property, Object val) {
        return where(property.toColumn().eq(val));
    }

    public <C> MapperWhereQ whereNeq(Property<C, ?> property, Object val) {
        return where(property.toColumn().neq(val));
    }

    public <C> MapperWhereQ whereLt(Property<C, ?> property, Object val) {
        return where(property.toColumn().lt(val));
    }

    public <C> MapperWhereQ whereLte(Property<C, ?> property, Object val) {
        return where(property.toColumn().lte(val));
    }

    public <C> MapperWhereQ whereGt(Property<C, ?> property, Object val) {
        return where(property.toColumn().gt(val));
    }

    public <C> MapperWhereQ whereGte(Property<C, ?> property, Object val) {
        return where(property.toColumn().gte(val));
    }

    public <C> MapperWhereQ whereLk(Property<C, ?> property, String val) {
        return where(property.toColumn().like(val));
    }

    public <C> MapperWhereQ whereNlk(Property<C, ?> property, String val) {
        return where(property.toColumn().nlike(val));
    }

    public <C> MapperWhereQ whereBtw(Property<C, ?> property, Object start, Object end) {
        return where(property.toColumn().between(start, end));
    }

    public <C> MapperWhereQ whereNbtw(Property<C, ?> property, Object start, Object end) {
        return where(property.toColumn().nbetween(start, end));
    }

    public <C> MapperWhereQ whereIn(Property<C, ?> property, Iterable ary) {
        return where(property.toColumn().in(ary));
    }
    public <C> MapperWhereQ whereNin(Property<C, ?> property, Iterable ary) {
        return where(property.toColumn().nin(ary));
    }


    public <C> MapperWhereQ andEq(Property<C, ?> property, Object val) {
        return and(property.toColumn().eq(val));
    }

    public <C> MapperWhereQ andNeq(Property<C, ?> property, Object val) {
        return and(property.toColumn().neq(val));
    }

    public <C> MapperWhereQ andLt(Property<C, ?> property, Object val) {
        return and(property.toColumn().lt(val));
    }

    public <C> MapperWhereQ andLte(Property<C, ?> property, Object val) {
        return and(property.toColumn().lte(val));
    }

    public <C> MapperWhereQ andGt(Property<C, ?> property, Object val) {
        return and(property.toColumn().gt(val));
    }

    public <C> MapperWhereQ andGte(Property<C, ?> property, Object val) {
        return and(property.toColumn().gte(val));
    }

    public <C> MapperWhereQ andLk(Property<C, ?> property, String val) {
        return and(property.toColumn().like(val));
    }

    public <C> MapperWhereQ andNlk(Property<C, ?> property, String val) {
        return and(property.toColumn().nlike(val));
    }

    public <C> MapperWhereQ andBtw(Property<C, ?> property, Object start, Object end) {
        return and(property.toColumn().between(start, end));
    }
    public <C> MapperWhereQ andNbtw(Property<C, ?> property, Object start, Object end) {
        return and(property.toColumn().nbetween(start, end));
    }
    public <C> MapperWhereQ andIn(Property<C, ?> property, Iterable ary) {
        return and(property.toColumn().in(ary));
    }

    public <C> MapperWhereQ andNin(Property<C, ?> property, Iterable ary) {
        return and(property.toColumn().nin(ary));
    }

    public <C> MapperWhereQ orEq(Property<C, ?> property, Object val) {
        return or(property.toColumn().eq(val));
    }

    public <C> MapperWhereQ orNeq(Property<C, ?> property, Object val) {
        return or(property.toColumn().neq(val));
    }

    public <C> MapperWhereQ orLt(Property<C, ?> property, Object val) {
        return or(property.toColumn().lt(val));
    }

    public <C> MapperWhereQ orLte(Property<C, ?> property, Object val) {
        return or(property.toColumn().lte(val));
    }

    public <C> MapperWhereQ orGt(Property<C, ?> property, Object val) {
        return or(property.toColumn().gt(val));
    }

    public <C> MapperWhereQ orGte(Property<C, ?> property, Object val) {
        return or(property.toColumn().gte(val));
    }

    public <C> MapperWhereQ orLk(Property<C, ?> property, String val) {
        return or(property.toColumn().like(val));
    }

    public <C> MapperWhereQ orNlk(Property<C, ?> property, String val) {
        return or(property.toColumn().nlike(val));
    }

    public <C> MapperWhereQ orBtw(Property<C, ?> property, Object start, Object end) {
        return or(property.toColumn().between(start, end));
    }
    public <C> MapperWhereQ orNbtw(Property<C, ?> property, Object start, Object end) {
        return or(property.toColumn().nbetween(start, end));
    }
    public <C> MapperWhereQ orIn(Property<C, ?> property, Iterable ary) {
        return or(property.toColumn().in(ary));
    }

    public <C> MapperWhereQ orNin(Property<C, ?> property, Iterable ary) {
        return or(property.toColumn().nin(ary));
    }


    public <C> MapperWhereQ orderByAsc(Property<C,?> property) {
        return orderByAsc(property.toColumn());
    }
    public <C> MapperWhereQ orderByDesc(Property<C,?> property) {
        return orderByDesc(property.toColumn());
    }
    public <C> MapperWhereQ andByAsc(Property<C,?> property) {
        return andByAsc(property.toColumn());
    }
    public <C> MapperWhereQ andByDesc(Property<C,?> property) {
        return andByDesc(property.toColumn());
    }


    public <C> MapperWhereQ groupBy(Property<C,?> property) {
        return groupBy(property.toColumn());
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
