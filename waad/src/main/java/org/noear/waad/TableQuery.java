package org.noear.waad;

import org.noear.waad.core.CommandImpl;
import org.noear.waad.link.IColumn;
import org.noear.waad.model.DataRow;

import java.sql.SQLException;
import java.util.Map;
import java.util.function.BiFunction;


/**
 * Created by noear on 14/11/12.
 *
 * $.       //当前表空间
 * $NOW()   //说明这里是一个sql 函数
 * ?
 * ?...     //说明这里是一个数组或查询结果
 */
public class TableQuery extends TableQueryBase<TableQuery> {
    protected DataRow _item = null; //会排除null数据

    public TableQuery(DbContext context) {
        super(context);
    }

    private void item_init(){
        if (_item == null) {
            _item = DataRow.create();
        }
    }

    public TableQuery set(String column, Object value) {
        item_init();
        _item.set(column, value);

        return this;
    }

    public TableQuery set(IColumn column, Object value) {
        return set(column.name(), value);
    }

    public TableQuery setInc(String column, long value) {
        usingExpr(true);
        if (value < 0) {
            set(column, "${" + column + value + "}");
        } else {
            set(column, "${" + column + "+" + value + "}");
        }

        return this;
    }

    public TableQuery setInc(IColumn column, long value) {
        return setInc(column.name(), value);
    }


    public TableQuery setDf(String column, Object value, Object def) {
        if (value == null) {
            set(column, def);
        } else {
            set(column, value);
        }

        return this;
    }

    public TableQuery setDf(IColumn column, Object value, Object def) {
        return setDf(column.name(), value, def);
    }

    public TableQuery setIf(boolean condition, String column, Object value){
        if(condition){
            set(column,value);
        }

        return this;
    }

    public TableQuery setIf(boolean condition, IColumn column, Object value) {
        return setIf(condition, column.getCode(), value);
    }

    public TableQuery setMap(Map<String,Object> data) {
        if (usingNull()) {
            setMapIf(data, (k, v) -> true);
        } else {
            setMapIf(data, (k, v) -> v != null);
        }

        return this;
    }

    public TableQuery setMapIf(Map<String,Object> data, BiFunction<String,Object,Boolean> condition) {
        item_init();
        _item.setMapIf(data, condition);

        return this;
    }

    public TableQuery setEntity(Object data) {
        if (usingNull()) {
            setEntityIf(data, (k, v) -> true);
        } else {
            setEntityIf(data, (k, v) -> v != null);
        }

        return this;
    }

    public TableQuery setEntityIf(Object data, BiFunction<String,Object,Boolean> condition) {
        item_init();
        _item.setEntityIf(data, condition);

        return this;
    }


    /**
     * 执行插入并返回自增值，使用set接口的数据
     * （默认，只会插入不是null的数据）
     * */
    public long insert() throws SQLException {
        if (_item == null) {
            return 0;
        } else {
            return insert(_item);
        }
    }

    public CommandImpl insertAsCmd() {
        if (_item == null) {
            return null;
        } else {
            return insertAsCmd(_item);
        }
    }

    /**
     * 根据字段和数据自动形成插入条件
     * */
    public long insertBy(String conditionFields)throws SQLException {
        if (_item == null) {
            return 0;
        } else {
            return insertBy(_item, conditionFields);
        }
    }

    /**
     * 执行更新并返回影响行数，使用set接口的数据
     * （默认，只会更新不是null的数据）
     * */
    public int update() throws SQLException {
        if (_item == null) {
            return 0;
        }
        else {
            return update(_item);
        }
    }

    public CommandImpl updateAsCmd() throws SQLException {
        if (_item == null) {
            return null;
        } else {
            return updateAsCmd(_item);
        }
    }

    public int updateBy(String conditionFields) throws SQLException {
        if (_item == null) {
            return 0;
        } else {
            return updateBy(_item, conditionFields);
        }
    }

    /**
     * 使用set接口的数据,根据约束字段自动插入或更新
     * （默认，只会更新不是null的数据）
     * */
    public long upsertBy(String conditionFields)throws SQLException {
        if (_item != null) {
            return upsertBy(_item, conditionFields);
        }else{
            return 0;
        }
    }
}