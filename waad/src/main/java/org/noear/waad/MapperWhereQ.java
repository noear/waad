package org.noear.waad;

import org.noear.waad.wrap.Property;
import org.noear.waad.wrap.PropertyWrap;

/**
 * Created by noear on 19-12-11.
 *
 * 仅用于BaseMapper，即用于单表操作
 */
public class MapperWhereQ extends WhereBase<MapperWhereQ> {

    private DbTableQuery _query;

    public MapperWhereQ(DbTableQuery query) {
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
        return whereEq(getColumnName(property), val);
    }

    public <C> MapperWhereQ whereNeq(Property<C, ?> property, Object val) {
        return whereNeq(getColumnName(property), val);
    }

    public <C> MapperWhereQ whereLt(Property<C, ?> property, Object val) {
        return whereLt(getColumnName(property), val);
    }

    public <C> MapperWhereQ whereLte(Property<C, ?> property, Object val) {
        return whereLte(getColumnName(property), val);
    }

    public <C> MapperWhereQ whereGt(Property<C, ?> property, Object val) {
        return whereGt(getColumnName(property), val);
    }

    public <C> MapperWhereQ whereGte(Property<C, ?> property, Object val) {
        return whereGte(getColumnName(property), val);
    }

    public <C> MapperWhereQ whereLk(Property<C, ?> property, String val) {
        return whereLk(getColumnName(property), val);
    }

    public <C> MapperWhereQ whereNlk(Property<C, ?> property, String val) {
        return whereNlk(getColumnName(property), val);
    }

    public <C> MapperWhereQ whereBtw(Property<C, ?> property, Object start, Object end) {
        return whereBtw(getColumnName(property), start, end);
    }

    public <C> MapperWhereQ whereNbtw(Property<C, ?> property, Object start, Object end) {
        return whereNbtw(getColumnName(property), start, end);
    }

    public <C> MapperWhereQ whereIn(Property<C, ?> property, Iterable ary) {
        return whereIn(getColumnName(property), ary);
    }
    public <C> MapperWhereQ whereNin(Property<C, ?> property, Iterable ary) {
        return whereNin(getColumnName(property), ary);
    }


    public <C> MapperWhereQ andEq(Property<C, ?> property, Object val) {
        return andEq(getColumnName(property), val);
    }

    public <C> MapperWhereQ andNeq(Property<C, ?> property, Object val) {
        return andNeq(getColumnName(property), val);
    }

    public <C> MapperWhereQ andLt(Property<C, ?> property, Object val) {
        return andLt(getColumnName(property), val);
    }

    public <C> MapperWhereQ andLte(Property<C, ?> property, Object val) {
        return andLte(getColumnName(property), val);
    }

    public <C> MapperWhereQ andGt(Property<C, ?> property, Object val) {
        return andGt(getColumnName(property), val);
    }

    public <C> MapperWhereQ andGte(Property<C, ?> property, Object val) {
        return andGte(getColumnName(property), val);
    }

    public <C> MapperWhereQ andLk(Property<C, ?> property, String val) {
        return andLk(getColumnName(property), val);
    }

    public <C> MapperWhereQ andNlk(Property<C, ?> property, String val) {
        return andNlk(getColumnName(property), val);
    }

    public <C> MapperWhereQ andBtw(Property<C, ?> property, Object start, Object end) {
        return andBtw(getColumnName(property), start, end);
    }
    public <C> MapperWhereQ andNbtw(Property<C, ?> property, Object start, Object end) {
        return andNbtw(getColumnName(property), start, end);
    }
    public <C> MapperWhereQ andIn(Property<C, ?> property, Iterable ary) {
        return andIn(getColumnName(property), ary);
    }

    public <C> MapperWhereQ andNin(Property<C, ?> property, Iterable ary) {
        return andNin(getColumnName(property), ary);
    }

    public <C> MapperWhereQ orEq(Property<C, ?> property, Object val) {
        return orEq(getColumnName(property), val);
    }

    public <C> MapperWhereQ orNeq(Property<C, ?> property, Object val) {
        return orNeq(getColumnName(property), val);
    }

    public <C> MapperWhereQ orLt(Property<C, ?> property, Object val) {
        return orLt(getColumnName(property), val);
    }

    public <C> MapperWhereQ orLte(Property<C, ?> property, Object val) {
        return orLte(getColumnName(property), val);
    }

    public <C> MapperWhereQ orGt(Property<C, ?> property, Object val) {
        return orGt(getColumnName(property), val);
    }

    public <C> MapperWhereQ orGte(Property<C, ?> property, Object val) {
        return orGte(getColumnName(property), val);
    }

    public <C> MapperWhereQ orLk(Property<C, ?> property, String val) {
        return orLk(getColumnName(property), val);
    }

    public <C> MapperWhereQ orNlk(Property<C, ?> property, String val) {
        return orNlk(getColumnName(property), val);
    }

    public <C> MapperWhereQ orBtw(Property<C, ?> property, Object start, Object end) {
        return orBtw(getColumnName(property), start, end);
    }
    public <C> MapperWhereQ orNbtw(Property<C, ?> property, Object start, Object end) {
        return orNbtw(getColumnName(property), start, end);
    }
    public <C> MapperWhereQ orIn(Property<C, ?> property, Iterable ary) {
        return orIn(getColumnName(property), ary);
    }

    public <C> MapperWhereQ orNin(Property<C, ?> property, Iterable ary) {
        return orNin(getColumnName(property), ary);
    }


    public <C> MapperWhereQ orderByAsc(Property<C,?> property) {
        return orderByAsc(getColumnName(property));
    }
    public <C> MapperWhereQ orderByDesc(Property<C,?> property) {
        return orderByDesc(getColumnName(property));
    }
    public <C> MapperWhereQ andByAsc(Property<C,?> property) {
        return andByAsc(getColumnName(property));
    }
    public <C> MapperWhereQ andByDesc(Property<C,?> property) {
        return andByDesc(getColumnName(property));
    }


    public <C> MapperWhereQ groupBy(Property<C,?> property) {
        return groupBy(getColumnName(property));
    }

    public MapperWhereQ limit(int size) {
        _query.limit(size);
        return this;
    }

    public MapperWhereQ limit(int start, int size) {
        _query.limit(start, size);
        return this;
    }

    protected <C> String getColumnName(Property<C, ?> p) {
        return PropertyWrap.get(p).name;
    }

}
