package org.noear.waad.mapper;

import org.noear.waad.*;
import org.noear.waad.linq.IColumn;
import org.noear.waad.model.*;
import org.noear.waad.util.function.Act1;
import org.noear.waad.util.function.Act2;
import org.noear.waad.linq.IColumnLinq;
import org.noear.waad.util.RunUtils;
import org.noear.waad.util.StrUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author noear
 * @since 19-12-11.
 * @since 4.0
 */
public class BaseMapperWrap<T> implements BaseMapper<T> {
    private DbContext _db;
    private BaseEntityWrap _table;
    private String _tabelName;

    private Class<?> _entityType;

    /**
     * 实体类型
     */
    protected Class<?> entityType() {
        return _entityType;
    }


    public BaseMapperWrap(DbContext db, Class<?> entityType, String tableName) {
        _db = db;
        _entityType = entityType; //给 BaseEntityWrap 用的
        _table = BaseEntityWrap.get(this);

        if (StrUtils.isEmpty(tableName)) {
            _tabelName = _table.tableName;
        } else {
            _tabelName = tableName;
        }
    }

    public BaseMapperWrap(DbContext db, BaseMapper<T> baseMapper) {
        _db = db;
        _table = BaseEntityWrap.get(baseMapper);
        _tabelName = _table.tableName;
    }

    @Override
    public DbContext db() {
        return _db;
    }

    /**
     * 表名
     */
    public String tableName() {
        return _tabelName;
    }

    /**
     * 主键
     */
    @Override
    public String tablePk() {
        return _table.pkName;
    }

    /**
     * 实体类
     */
    @Override
    public Class<?> entityClz() {
        return _table.entityClz;
    }


    @Override
    public Long insert(T entity, boolean excludeNull) {
        DataRow data = DataRow.create();

        if (excludeNull) {
            data.setEntityIf(entity, (k, v) -> v != null);
        } else {
            data.setEntityIf(entity, (k, v) -> true);
        }

        return RunUtils.call(()
                -> getQr().insert(data));
    }

    /**
     * 插入数据，由外部组装数据
     *
     * @param entity      写入的实体
     * @param dataBuilder 数据组装器
     * @return
     */
    @Override
    public Long insert(T entity, Act2<T, DataRow> dataBuilder) {
        DataRow data = DataRow.create();
        dataBuilder.run(entity, data);

        return RunUtils.call(() -> getQr().insert(data));
    }

    @Override
    public void insertList(List<T> list) {
        List<DataRow> list2 = new ArrayList<>();
        for (T d : list) {
            list2.add(DataRow.create().setEntityIf(d, (k, v) -> true));
        }

        RunUtils.call(()
                -> getQr().insertList(list2));
    }

    /**
     * 批量插入数据
     *
     * @param list        待插入的数据
     * @param dataBuilder 数据组装器
     */
    @Override
    public void insertList(List<T> list, Act2<T, DataRow> dataBuilder) {
        List<DataRow> list2 = new ArrayList<>();
        for (T d : list) {
            DataRow data = DataRow.create();
            dataBuilder.run(d, data);
            list2.add(data);
        }

        RunUtils.call(()
                -> getQr().insertList(list2));
    }


    @Override
    public Integer deleteById(Object id) {
        return RunUtils.call(()
                -> getQr().where(new IColumnLinq(tablePk()).eq(id)).delete());
    }

    @Override
    public Integer deleteByIds(Iterable idList) {
        return RunUtils.call(()
                -> getQr().where(new IColumnLinq(tablePk()).in(idList)).delete());
    }

    @Override
    public Integer deleteByMap(Map<String, Object> columnMap) {
        return RunUtils.call(()
                -> getQr().whereMap(columnMap).delete());
    }

    @Override
    public Integer delete(Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> {
            return getQr(c).delete();
        });
    }

    @Override
    public Integer updateById(T entity, boolean excludeNull) {
        DataRow data = DataRow.create();

        if (excludeNull) {
            data.setEntityIf(entity, (k, v) -> v != null);
        } else {
            data.setEntityIf(entity, (k, v) -> true);
        }

        Object id = data.get(tablePk());

        return RunUtils.call(()
                -> getQr().where(new IColumnLinq(tablePk()).eq(id)).update(data));
    }

    /**
     * @param entity      待更新的实体
     * @param dataBuilder 组装data的方式，方便支持部分属性允许设置为null，部分不允许
     */
    @Override
    public Integer updateById(T entity, Act2<T, DataRow> dataBuilder) {
        DataRow data = DataRow.create();

        dataBuilder.run(entity, data);

        Object id = data.get(tablePk());

        return RunUtils.call(()
                -> getQr().where(new IColumnLinq(tablePk()).eq(id)).update(data));
    }

    @Override
    public Integer update(T entity, boolean excludeNull, Act1<MapperWhereQ> c) {
        DataRow data = DataRow.create();

        if (excludeNull) {
            data.setEntityIf(entity, (k, v) -> v != null);
        } else {
            data.setEntityIf(entity, (k, v) -> true);
        }

        return RunUtils.call(() -> {
            return getQr(c).update(data);
        });
    }

    /**
     * @param entity      待更新的实体
     * @param dataBuilder 组装data的方式，方便支持部分属性允许设置为null，部分不允许
     * @param c           更新数据的条件
     * @return
     */
    @Override
    public Integer update(T entity, Act2<T, DataRow> dataBuilder, Act1<MapperWhereQ> c) {
        DataRow data = DataRow.create();

        dataBuilder.run(entity, data);

        return RunUtils.call(() -> {
            return getQr(c).update(data);
        });
    }

    @Override
    public int[] updateList(List<T> list, Act2<T, DataRow> dataBuilder, IColumn... conditionColumns) {
        if (conditionColumns.length == 0) {
            throw new RuntimeException("Please enter constraints");
        }

        return updateList(list, dataBuilder, IColumn.getNemes(_db, conditionColumns));
    }

    @Override
    public int[] updateList(List<T> list, Act2<T, DataRow> dataBuilder, String conditionColumns) {
        if (StrUtils.isEmpty(conditionColumns)) {
            throw new RuntimeException("Please enter constraints");
        }

        return RunUtils.call(()
                -> getQr().updateList(list, dataBuilder, conditionColumns));
    }

    @Override
    public Long upsert(T entity, boolean excludeNull) {
        DataRow data = DataRow.create();

        if (excludeNull) {
            data.setEntityIf(entity, (k, v) -> v != null);
        } else {
            data.setEntityIf(entity, (k, v) -> true);
        }

        Object id = data.get(tablePk());

        if (id == null) {
            return RunUtils.call(() -> getQr().insert(data));
        } else {
            return RunUtils.call(() -> getQr().upsertBy(data, tablePk()));
        }
    }

    /**
     * 新增或修改数据 更新时根据主键更新
     *
     * @param entity      要处理的实体
     * @param dataBuilder 数据组装器
     * @return
     */
    @Override
    public Long upsert(T entity, Act2<T, DataRow> dataBuilder) {
        DataRow data = DataRow.create();

        dataBuilder.run(entity, data);

        Object id = data.get(tablePk());

        if (id == null) {
            return RunUtils.call(() -> getQr().insert(data));
        } else {
            return RunUtils.call(() -> getQr().upsertBy(data, tablePk()));
        }
    }

    @Override
    public Long upsertBy(T entity, boolean excludeNull, IColumn... conditionColumns) {
        return upsertBy(entity, excludeNull, IColumn.getNemes(_db, conditionColumns));
    }

    @Override
    public Long upsertBy(T entity, boolean excludeNull, String conditionColumns) {
        DataRow data = DataRow.create();

        if (excludeNull) {
            data.setEntityIf(entity, (k, v) -> v != null);
        } else {
            data.setEntityIf(entity, (k, v) -> true);
        }

        return RunUtils.call(() -> getQr().upsertBy(data, conditionColumns));
    }

    public Long upsertBy(T entity, Act2<T, DataRow> dataBuilder, IColumn... conditionColumns) {
        return upsertBy(entity, dataBuilder, IColumn.getNemes(_db, conditionColumns));
    }

    /**
     * 新增或修改数据 更新时根据条件字段更新
     *
     * @param entity           要处理的实体
     * @param dataBuilder      数据组装器
     * @param conditionColumns 更新的条件
     * @return
     */
    @Override
    public Long upsertBy(T entity, Act2<T, DataRow> dataBuilder, String conditionColumns) {
        DataRow data = DataRow.create();

        dataBuilder.run(entity, data);

        return RunUtils.call(() -> getQr().upsertBy(data, conditionColumns));
    }

    @Override
    public boolean existsById(Object id) {
        return RunUtils.call(()
                -> getQr().where(new IColumnLinq(tablePk()).eq(id)).selectExists());
    }

    @Override
    public boolean exists(Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> {
            return getQr(c).selectExists();
        });
    }

    @Override
    public T selectById(Object id) {
        Class<T> clz = (Class<T>) entityClz();

        return RunUtils.call(()
                -> getQr().where(new IColumnLinq(tablePk()).eq(id)).limit(1).selectItem(clz, "*"));
    }

    @Override
    public List<T> selectByIds(Iterable idList) {
        Class<T> clz = (Class<T>) entityClz();

        return RunUtils.call(()
                -> getQr().where(new IColumnLinq(tablePk()).in(idList)).selectList(clz, "*"));
    }

    @Override
    public List<T> selectByMap(Map<String, Object> columnMap) {
        Class<T> clz = (Class<T>) entityClz();

        return RunUtils.call(()
                -> getQr().whereMap(columnMap).selectList(clz, "*"));
    }

    @Override
    public T selectItem(T entity) {
        Class<T> clz = (Class<T>) entityClz();

        return RunUtils.call(() ->
                getQr().whereEntity(entity).limit(1).selectItem(clz, "*"));
    }

    @Override
    public T selectItem(Act1<MapperWhereQ> c) {
        Class<T> clz = (Class<T>) entityClz();

        return RunUtils.call(() -> getQr(c).selectItem(clz, "*"));
    }

    @Override
    public Object selectValue(String column, Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> getQr(c).selectValue(column));
    }

    @Override
    public Map<String, Object> selectMap(Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> getQr(c).selectMap("*"));
    }

    @Override
    public Long selectCount(Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> getQr(c).selectCount());
    }

    @Override
    public List<T> selectList(Act1<MapperWhereQ> c) {
        Class<T> clz = (Class<T>) entityClz();

        return RunUtils.call(() -> getQr(c).selectList(clz, "*"));
    }

    @Override
    public List<Map<String, Object>> selectMapList(Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> getQr(c).selectMapList("*"));
    }

    @Override
    public List<Object> selectArray(String column, Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> getQr(c).selectArray(column));
    }

    @Override
    public List<T> selectList(int start, int size, Act1<MapperWhereQ> c) {
        Class<T> clz = (Class<T>) entityClz();

        return RunUtils.call(() -> getQr(c).limit(start, size).selectList(clz, "*"));
    }

    @Override
    public List<Map<String, Object>> selectMapList(int start, int size, Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> getQr(c).limit(start, size).selectMapList("*"));
    }

    @Override
    public List<Object> selectArray(String column, int start, int size, Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> getQr(c).limit(start, size).selectArray(column));
    }


    @Override
    public DataReader<T> selectReader(Act1<MapperWhereQ> c) {
        return selectReader(0, c);
    }

    @Override
    public DataReader<T> selectReader(int fetchSize, Act1<MapperWhereQ> c) {
        Class<T> clz = (Class<T>) entityClz();

        DataReaderForDataRow reader = RunUtils.call(() -> getQr(c).fetchSize(fetchSize).selectDataReader("*"));
        return reader.toEntityReader(clz);
    }

    @Override
    public Page<T> selectPage(int start, int size, Act1<MapperWhereQ> c) {
        Class<T> clz = (Class<T>) entityClz();

        List<T> list = RunUtils.call(() -> getQr(c).limit(start, size).selectList(clz, "*"));
        long total = RunUtils.call(() -> getQr(c).selectCount());

        PageImpl<T> page = new PageImpl<>(list, total, size);

        return page;
    }

    @Override
    public Page<Map<String, Object>> selectMapPage(int start, int size, Act1<MapperWhereQ> c) {
        List<Map<String, Object>> list = RunUtils.call(() -> getQr(c).limit(start, size).selectMapList("*"));
        long total = RunUtils.call(() -> getQr(c).selectCount());

        PageImpl<Map<String, Object>> page = new PageImpl<>(list, total, size);

        return page;
    }

    @Override
    public List<T> selectTop(int size, Act1<MapperWhereQ> c) {
        Class<T> clz = (Class<T>) entityClz();

        return RunUtils.call(() -> getQr(c).top(size).selectList(clz, "*"));
    }

    @Override
    public List<Map<String, Object>> selectMapTop(int size, Act1<MapperWhereQ> c) {
        return RunUtils.call(() -> getQr(c).top(size).selectMapList("*"));
    }

    /**
     * 获取查询器
     */
    protected TableQuery getQr() {
        return db().table(tableName());
    }

    /**
     * 获取带条件的查询器
     */
    protected TableQuery getQr(Act1<MapperWhereQ> c) {
        TableQuery qr = db().table(tableName());

        if (c != null) {
            c.run(new MapperWhereQ(qr));
        }

        return qr;
    }
}
