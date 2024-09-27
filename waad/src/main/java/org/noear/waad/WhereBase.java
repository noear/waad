package org.noear.waad;

import org.noear.waad.core.SQLBuilder;
import org.noear.waad.utils.fun.Fun2;
import org.noear.waad.link.IColumn;
import org.noear.waad.link.ICondition;
import org.noear.waad.utils.EntityUtils;
import org.noear.waad.wrap.DbType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by noear on 19-12-11.
 */
public abstract class WhereBase<T extends WhereBase> {
    protected DbContext  _context;
    protected SQLBuilder _builder;
    protected StringBuilder _orderBy;

    protected boolean _hasGroup = false;

    protected WhereBase() {

    }

    public WhereBase(DbContext context) {
        _context = context;
        _builder = new SQLBuilder();
    }

    protected DbType dbType(){
        return _context.getMetaData().getType();
    }

    protected String fmtSchema(String name){
        return _context.formater().formatSchema(name);
    }
    protected String fmtObject(String name) {
        return _context.formater().formatTable(name);
    }

    protected String fmtColumn(String name) {
        return _context.formater().formatColumn(name);
    }

    protected String fmtMutColumns(String columns) {
        return _context.formater().formatMultipleColumns(columns);
    }

    protected String fmtCondition(String condition) {
        return _context.formater().formatCondition(condition);
    }


    public T whereTrue(){
        return where("1=1");
    }

    /**
     * 添加SQL where 语句
     * 可使用?,?...占位符（ ?... 表示数组占位符）
     * <p>
     * 例1: .where("name=?","x");
     * 例2: .where("((name=? or id=?) and sex=0)","x",1)
     * 例3: .where("id IN (?...)",new int[]{1,12,3,6})
     */
    public T where(String code, Object... args) {
        _builder.append(" WHERE ").append(fmtCondition(code), args);
        return (T) this;
    }

    public T where(ICondition condition) {
        _builder.append(" WHERE ").append(fmtColumn(condition.getColumn().getCode())).append(condition.getDescription(), condition.getArgs());
        return (T) this;
    }

    //
    // whereIf //非常危险。。。已对delete(),update()添加限有制
    public T whereIf(boolean condition, String code, Object... args) {
        if (condition) {
            where(code, args);
        }
        return (T) this;
    }


    /**
     * 添加SQL where 关键字
     */
    public T where() {
        _builder.append(" WHERE ");
        return (T) this;
    }

    public T where(MapperWhereQ whereQ) {
        _builder.append(whereQ._builder);
        return (T) this;
    }

    public T whereMap(Map<String, Object> columnMap) {
        return whereMapIf(columnMap, (k, v) -> v != null);
    }

    public T whereMapIf(Map<String, Object> columnMap, Fun2<Boolean,String,Object> condition) {
        if (columnMap != null && columnMap.size() > 0) {
            where("1=1");
            columnMap.forEach((k, v) -> {
                if (condition.run(k, v)) {
                    and(fmtColumn(k) + " = ? ", v);
                }
            });
        }
        return (T) this;
    }

    public T whereEntity(Object entity) {
        return whereEntityIf(entity, (k, v) -> v != null);
    }

    public T whereEntityIf(Object entity, Fun2<Boolean,String,Object> condition) {
        if (entity != null) {
            where("1=1");
            EntityUtils.fromEntity(entity, (k, v) -> {
                if (condition.run(k, v)) {
                    and(fmtColumn(k) + " = ? ", v);
                }
            });
        }
        return (T) this;
    }

    /**
     * 添加SQL and 语句 //可使用?占位符
     * <p>
     * 例1：.and("name=?","x");
     * 例2: .and("(name=? or id=?)","x",1)
     */
    public T and(String code, Object... args) {
        _builder.append(" AND ").append(fmtCondition(code), args);
        return (T) this;
    }

    public T and(ICondition condition) {
        _builder.append(" AND ").append(fmtColumn(condition.getColumn().getCode())).append(condition.getDescription(), condition.getArgs());
        return (T) this;
    }

    public T andIf(boolean condition, String code, Object... args) {
        if (condition) {
            and(code, args);
        }
        return (T) this;
    }

    /**
     * 添加SQL and 关键字
     */
    public T and() {
        _builder.append(" AND ");
        return (T) this;
    }


    /**
     * 添加SQL or 语句 //可使用?占位符
     * 例1：.or("name=?","x");
     * 例2: .or("(name=? or id=?)","x",1)
     */
    public T or(String code, Object... args) {
        _builder.append(" OR ").append(fmtCondition(code), args);
        return (T) this;
    }

    public T or(ICondition condition) {
        _builder.append(" OR ").append(fmtColumn(condition.getColumn().getCode())).append(condition.getDescription(), condition.getArgs());
        return (T) this;
    }

    public T orIf(boolean condition, String code, Object... args) {
        if (condition) {
            or(code, args);
        }
        return (T) this;
    }

    /**
     * 添加SQL or 关键字
     */
    public T or() {
        _builder.append(" OR ");
        return (T) this;
    }


    /**
     * 添加左括号
     */
    public T begin() {
        _builder.append(" ( ");
        return (T) this;
    }

    /**
     * 添加左括号并附加代码
     * 可使用?,?...占位符（ ?... 表示数组占位符）
     */
    public T begin(String code, Object... args) {
        _builder.append(" ( ").append(fmtCondition(code), args);
        return (T) this;
    }

    public T begin(ICondition condition) {
        _builder.append(" ( ").append(fmtColumn(condition.getColumn().getCode())).append(condition.getDescription(), condition.getArgs());
        return (T) this;
    }

    /**
     * 添加右括号
     */
    public T end() {
        _builder.append(" ) ");
        return (T) this;
    }

    protected T orderByDo(String code) {
        if(_orderBy == null){
            _orderBy = new StringBuilder(" ORDER BY ");
        }else{
            _orderBy.append(", ");
        }

        _orderBy.append(code);

        return (T) this;
    }

    public T orderBy(String code) {
        return orderByDo(fmtMutColumns(code));
    }


    public T orderByAsc(IColumn... columns) {
        assert columns.length > 0;
        String columnsStr = Arrays.stream(columns).map(c->c.getCode()).collect(Collectors.joining(","));

        return orderByDo(fmtMutColumns(columnsStr) + " ASC ");
    }

    public T orderByDesc(IColumn... columns) {
        assert columns.length > 0;
        String columnsStr = Arrays.stream(columns).map(c->c.getCode()).collect(Collectors.joining(","));


        return orderByDo(fmtMutColumns(columnsStr) + " DESC ");
    }

    public T andBy(String code) {
        return orderByDo(fmtMutColumns(code));
    }

    public T andByAsc(IColumn... columns) {
        assert columns.length > 0;
        String columnsStr = Arrays.stream(columns).map(c->c.getCode()).collect(Collectors.joining(","));

        return orderByDo(fmtMutColumns(columnsStr) + " ASC ");
    }

    public T andByDesc(IColumn... columns) {
        assert columns.length > 0;
        String columnsStr = Arrays.stream(columns).map(c->c.getCode()).collect(Collectors.joining(","));

        return orderByDo(fmtMutColumns(columnsStr) + " DESC ");
    }

    public T groupBy(String columns) {
        _builder.append(" GROUP BY ").append(fmtMutColumns(columns));
        _hasGroup = true;
        return (T)this;
    }

    public T groupBy(IColumn... columns) {
        assert columns.length > 0;
        String columnsStr = Arrays.stream(columns).map(c->c.getCode()).collect(Collectors.joining(","));

        return groupBy(columnsStr);
    }

    public T having(String code){
        _builder.append(" HAVING ").append(code);
        return (T)this;
    }
}