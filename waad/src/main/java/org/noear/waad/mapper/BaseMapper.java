package org.noear.waad.mapper;

import org.noear.waad.*;
import org.noear.waad.model.DataRow;
import org.noear.waad.model.DataReader;
import org.noear.waad.model.Page;
import org.noear.waad.util.function.Act1;
import org.noear.waad.util.function.Act2;
import org.noear.waad.wrap.Property;

import java.util.List;
import java.util.Map;

/**
 * @author noear
 */
public interface BaseMapper<T> extends Mapper{
    /**
     * 当前数据源
     */
    DbContext db();

    /**
     * 当前表名
     */
    String tableName();

    /**
     * 当前表主键
     */
    String tablePk();

    /**
     * 当前实体类
     */
    Class<?> entityClz();


    /**
     * 插入数据，根据excludeNull决定是否要拼接值为null的数据
     *
     * @param entity      写入的实体
     * @param excludeNull 是否排除null属性
     * @return
     */
    Long insert(T entity, boolean excludeNull);

    /**
     * 插入数据，由外部组装数据
     *
     * @param entity      写入的实体
     * @param dataBuilder 数据组装器
     * @return
     */
    Long insert(T entity, Act2<T, DataRow> dataBuilder);

    /**
     * 批量插入数据
     *
     * @param list
     */
    void insertList(List<T> list);


    /**
     * 批量插入数据
     *
     * @param list        待插入的数据
     * @param dataBuilder 数据组装器
     */
    void insertList(List<T> list, Act2<T, DataRow> dataBuilder);

    Integer deleteById(Object id);

    Integer deleteByIds(Iterable idList);

    Integer deleteByMap(Map<String, Object> columnMap);

    Integer delete(Act1<MapperWhereQ> condition);

    /**
     * @param excludeNull 排除null
     */
    Integer updateById(T entity, boolean excludeNull);

    /**
     * @param entity      待更新的实体
     * @param dataBuilder 组装data的方式，方便支持部分属性允许设置为null，部分不允许
     */
    Integer updateById(T entity, Act2<T, DataRow> dataBuilder);

    Integer update(T entity, boolean excludeNull, Act1<MapperWhereQ> condition);

    /**
     * @param entity      待更新的实体
     * @param dataBuilder 组装data的方式，方便支持部分属性允许设置为null，部分不允许
     * @param condition   更新数据的条件
     * @return
     */
    Integer update(T entity, Act2<T, DataRow> dataBuilder, Act1<MapperWhereQ> condition);

    int[] updateList(List<T> list, Act2<T, DataRow> dataBuilder, Property<T, ?>... conditionFields);

    /**
     * 新增或修改数据 更新时根据主键更新
     *
     * @param entity      要处理的实体
     * @param excludeNull 是否排除null值
     * @return
     */
    Long upsert(T entity, boolean excludeNull);

    /**
     * 新增或修改数据 更新时根据主键更新
     *
     * @param entity      要处理的实体
     * @param dataBuilder 数据组装器
     * @return
     */
    Long upsert(T entity, Act2<T, DataRow> dataBuilder);


    /**
     * 新增或修改数据 更新时根据条件字段更新
     *
     * @param entity          要处理的实体
     * @param excludeNull     是否排除null值
     * @param conditionFields 更新的条件
     * @return
     */
    Long upsertBy(T entity, boolean excludeNull, String conditionFields);

    /**
     * 新增或修改数据 更新时根据条件字段更新
     *
     * @param entity          要处理的实体
     * @param dataBuilder     数据组装器
     * @param conditionFields 更新的条件
     * @return
     */
    Long upsertBy(T entity, Act2<T, DataRow> dataBuilder, String conditionFields);


    boolean existsById(Object id);

    boolean exists(Act1<MapperWhereQ> condition);

    T selectById(Object id);

    List<T> selectByIds(Iterable idList);

    List<T> selectByMap(Map<String, Object> columnMap);

    T selectItem(T entity);

    T selectItem(Act1<MapperWhereQ> condition);

    Map<String, Object> selectMap(Act1<MapperWhereQ> condition);

    Object selectValue(String column, Act1<MapperWhereQ> condition);

    Long selectCount(Act1<MapperWhereQ> condition);

    List<T> selectList(Act1<MapperWhereQ> condition);

    List<Map<String, Object>> selectMapList(Act1<MapperWhereQ> condition);

    List<Object> selectArray(String column, Act1<MapperWhereQ> condition);


    List<T> selectList(int start, int size, Act1<MapperWhereQ> condition);

    List<Map<String, Object>> selectMapList(int start, int size, Act1<MapperWhereQ> condition);

    List<Object> selectArray(String column, int start, int size, Act1<MapperWhereQ> condition);

    DataReader<T> selectReader(Act1<MapperWhereQ> condition);

    DataReader<T> selectReader(int fetchSize, Act1<MapperWhereQ> condition);


    /**
     * @param start 从0开始
     */
    Page<T> selectPage(int start, int size, Act1<MapperWhereQ> condition);

    Page<Map<String, Object>> selectMapPage(int start, int size, Act1<MapperWhereQ> condition);

    List<T> selectTop(int size, Act1<MapperWhereQ> condition);

    List<Map<String, Object>> selectMapTop(int size, Act1<MapperWhereQ> condition);
}
