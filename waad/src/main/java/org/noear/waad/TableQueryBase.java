package org.noear.waad;

import org.noear.waad.cache.CacheUsing;
import org.noear.waad.cache.ICacheController;
import org.noear.waad.cache.ICacheService;
import org.noear.waad.core.DbCommandImpl;
import org.noear.waad.core.Resultable;
import org.noear.waad.core.SQLBuilder;
import org.noear.waad.linq.ICondition;
import org.noear.waad.model.*;
import org.noear.waad.util.StrUtils;
import org.noear.waad.util.function.Act1;
import org.noear.waad.util.function.Act2;
import org.noear.waad.linq.IColumn;
import org.noear.waad.linq.IExpr;
import org.noear.waad.linq.ITable;
import org.noear.waad.linq.ITableLinq;
import org.noear.waad.core.DbType;
import org.noear.waad.util.function.GetHandler;

import java.sql.SQLException;
import java.util.*;


/**
 * Created by noear on 14/11/12.
 *
 * $.       //当前表空间
 * $NOW()   //说明这里是一个sql 函数
 * ?
 * ?...     //说明这里是一个数组或查询结果
 */
public class TableQueryBase<T extends TableQueryBase> extends WhereBase<T> implements ICacheController<TableQueryBase> {

    private String _table_raw;
    private ITable _table; //表名

    private  SQLBuilder _builder_bef;
    private  int _isLog = 0;

    public TableQueryBase(DbContext context) {
        super(context);
        _builder_bef = new SQLBuilder();
    }


    /**
     * 标记是否记录日志
     */
    public T log(boolean isLog) {
        _isLog = isLog ? 1 : -1;
        return (T) this;
    }



    /**
     * 通过表达式构建自己
     */
    public T build(Act1<T> builder) {
        builder.run((T) this);
        return (T) this;
    }

    protected T table(ITable table) {
        _table = table;
        return (T) this;
    }

    protected T table(String table) { //相当于 from
        if (table.startsWith("#")) {
            String _tableName = table.substring(1);

            _table_raw = _tableName;
            _table = new ITableLinq(_tableName, null);
        } else {
            _table_raw = table;

            if (table.indexOf('.') > 0) {
                _table = new ITableLinq(table, null);
            } else {
                _table = new ITableLinq(fmtObject(table), null); //"$." + table;
            }
        }

        return (T) this;
    }

    /**
     * 添加SQL with 语句（要确保数据库支持）
     * <p>
     * 例：db.table("user u")
     * .with("a","select type num from group by type")
     * .where("u.type in(select a.type) and u.type2 in (select a.type)")
     * .selectMapList("u.*");
     */
    public T with(String name, String code, Object... args) {
        if (_builder_bef.length() < 6) {
            _builder_bef.append(" WITH ");
        } else {
            _builder_bef.append(",");
        }

        _builder_bef.append(fmtColumn(name))
                .append(" AS (")
                .append(code, args)
                .append(") ");

        return (T) this;
    }

    public T with(String name, SelectQ select) {
        if (_builder_bef.length() < 6) {
            _builder_bef.append(" WITH ");
        } else {
            _builder_bef.append(",");
        }

        _builder_bef.append(fmtColumn(name))
                .append(" AS (")
                .append(select)
                .append(") ");

        return (T) this;
    }


    /**
     * 添加 FROM 语句
     */
    public T from(String table) {
        _builder.append(" FROM ").append(fmtObject(table));
        return (T) this;
    }

    public T from(ITable table) {
        return table(table.__getTableSpec().getCode(_context));
    }


    private T join(String style, String table) {
        if (table.startsWith("#")) {
            _builder.append(style).append(table.substring(1));
        } else {
            _builder.append(style).append(fmtObject(table));
        }
        return (T) this;
    }

    /**
     * 添加SQL 内关联语句
     */
    public T innerJoin(String table) {
        return join(" INNER JOIN ", table);
    }

    public T innerJoin(ITable table) {
        return innerJoin(table.__getTableSpec().getCode(_context));
    }

    /**
     * 添加SQL 左关联语句
     */
    public T leftJoin(String table) {
        return join(" LEFT JOIN ", table);
    }

    public T leftJoin(ITable table) {
        return leftJoin(table.__getTableSpec().getCode(_context));
    }

    /**
     * 添加SQL 右关联语句
     */
    public T rightJoin(String table) {
        return join(" RIGHT JOIN ", table);
    }

    public T rightJoin(ITable table) {
        return rightJoin(table.__getTableSpec().getCode(_context));
    }

    /**
     * 添加无限制代码
     */
    public T append(String code, Object... args) {
        _builder.append(code, args);
        return (T) this;
    }

    public T on(String code) {
        _builder.append(" ON ").append(code);
        return (T) this;
    }

    public T on(ICondition condition) {
        _builder.append(" ON ");
        condition.write(_context, _builder);
        return (T) this;
    }



    /**
     * 执行插入并返回自增值，使用dataBuilder构建的数据
     */
    public long insert(Act1<DataRow> dataBuilder) throws SQLException {
        DataRow item = DataRow.create();
        dataBuilder.run(item);

        return insert(item);
    }

    /**
     * 执行插入并返回自增值，使用data数据
     */
    public long insert(DataRow data) throws SQLException {
        if (data == null || data.size() == 0) {
            return 0;
        }

        return insertCompile(data).insert();
    }

    /**
     * 插入编译并获取命令
     * */
    public DbCommandImpl insertAsCmd(DataRow data) {
        if (data == null || data.size() == 0) {
            return null;
        }

        return insertCompile(data).getCommand();
    }

    /**
     * 插入编译
     * */
    protected DbQuery insertCompile(DataRow data) {
        _builder.clear();

        _context.metaData().getDialect()
                .buildInsertOneCode(_context, _table.__getTableSpec().name(), _builder, this::isSqlExpr, _usingNull, data);

        return compile();
    }


    /**
     * 根据约束进行插入
     */
    public long insertBy(DataRow data, String conditionFields) throws SQLException {
        if (data == null || data.size() == 0) {
            return 0;
        }

        String[] ff = conditionFields.split(",");

        if (ff.length == 0) {
            throw new RuntimeException("Please enter constraints");
        }

        this.where("1=1");

        for (String f : ff) {
            this.and(fmtColumn(f) + " = ? ", data.get(f));
        }

        if (this.selectExists()) {
            return 0;
        }

        return insert(data);
    }

    /**
     * 执行批量合并插入，使用集合数据
     */
    public boolean insertList(List<DataRow> valuesList) throws SQLException {
        if (valuesList == null) {
            return false;
        }

        return insertList(valuesList.get(0), valuesList);
    }

    /**
     * 执行批量合并插入，使用集合数据（由dataBuilder构建数据）
     */
    public <T> boolean insertList(Collection<T> valuesList, Act2<T, DataRow> dataBuilder) throws SQLException {
        List<DataRow> list2 = new ArrayList<>();

        for (T values : valuesList) {
            DataRow item = DataRow.create();
            dataBuilder.run(values, item);

            list2.add(item);
        }


        if (list2.size() > 0) {
            return insertList(list2.get(0), list2);
        } else {
            return false;
        }
    }


    protected <T extends GetHandler> boolean insertList(DataRow cols, Collection<T> valuesList) throws SQLException {
        if (valuesList == null || valuesList.size() == 0) {
            return false;
        }

        if (cols == null || cols.size() == 0) {
            return false;
        }

        _builder.backup();

        _context.metaData().getDialect().buildInsertOneCode(_context, _table.__getTableSpec().name(), _builder, this::isSqlExpr, true, cols);

        List<Object[]> argList = new ArrayList<>();
        String tml = _builder.toString();

        for (GetHandler item : valuesList) {
            List<Object> tmp = new ArrayList<>();
            for (String key : cols.keySet()) {
                tmp.add(item.get(key));
            }

            argList.add(tmp.toArray());
        }


        _builder.clear();
        _builder.append(tml, argList.toArray());

        int[] rst = compile().executeBatch();

        _builder.restore();


        return rst.length > 0;
    }


    /**
     * 使用data的数据,根据约束字段自动插入或更新
     */
    public long upsertBy(DataRow data, String conditionColumns) throws SQLException {
        if (data == null || data.size() == 0) {
            return 0;
        }

        String[] ff = conditionColumns.split(",");

        if (ff.length == 0) {
            throw new RuntimeException("Please enter constraints");
        }

        this.where("1=1");
        for (String f : ff) {
            this.and(fmtColumn(f) + " = ? ", data.get(f));
        }

        if (this.selectExists()) {
            for (String f : ff) {
                data.remove(f);
            }

            return this.update(data);
        } else {
            return this.insert(data);
        }
    }

    public long upsertBy(DataRow data, IColumn... conditionColumns) throws SQLException {
        return upsertBy(data, IExpr.getCodes(_context, conditionColumns));
    }

    public int updateBy(DataRow data, String conditionColumns) throws SQLException {
        String[] ff = conditionColumns.split(",");

        if (ff.length == 0) {
            throw new RuntimeException("Please enter constraints");
        }

        this.whereTrue();

        for (String f : ff) {
            this.and(fmtColumn(f) + " = ? ", data.get(f));
        }

        return update(data);
    }

    public int updateBy(DataRow data, IColumn... conditionColumns) throws SQLException {
        return updateBy(data, IExpr.getCodes(_context, conditionColumns));
    }

    /**
     * 执行更新并返回影响行数，使用dataBuilder构建的数据
     */
    public int update(Act1<DataRow> dataBuilder) throws SQLException {
        DataRow item = DataRow.create();
        dataBuilder.run(item);

        return update(item);
    }

    /**
     * 执行更新并返回影响行数，使用set接口的数据
     */
    public int update(DataRow data) throws SQLException {
        if (data == null || data.size() == 0) {
            return 0;
        }


        return updateCompile(data).execute();
    }

    /**
     * 更新编译并返回命令
     * */
    public DbCommandImpl updateAsCmd(DataRow data) {
        if (data == null || data.size() == 0) {
            return null;
        }


        return updateCompile(data).getCommand();
    }

    /**
     * 更新编译
     * */
    protected DbQuery updateCompile(DataRow data)  {
        if (WaadConfig.isUpdateMustConditional && _builder.indexOf(" WHERE ") < 0) {
            throw new RuntimeException("Lack of update condition!!!");
        }

        StringBuilder updateSb = new StringBuilder();
        _context.metaData().getDialect().updateCmdBegin(updateSb, _table.__getTableSpec().name());

        List<Object> setArgs = new ArrayList<Object>();
        StringBuilder setSb = new StringBuilder();

        _context.metaData().getDialect().updateCmdSet(setSb, _table.__getTableSpec().name());
        updateItemsBuild0(data, setSb, setArgs);

        _builder.backup();
        _builder.insert(updateSb.toString());
        _builder.insertBySymbol(" WHERE ",setSb.toString(), setArgs.toArray());


        if (limit_top > 0) {
            if (dbType() == DbType.MySQL || dbType() == DbType.MariaDB) {
                _builder.append(" LIMIT ?", limit_top);
            }
        }

        DbQuery query = compile();

        _builder.restore();

        return query;
    }

    private void updateItemsBuild0(DataRow data, StringBuilder buf, List<Object> args) {
        data.forEach((key, value) -> {
            if (value == null) {
                if (_usingNull) {
                    buf.append(fmtColumn(key)).append("=null,");
                }
                return;
            }

            if (value instanceof String) {
                String val2 = (String) value;
                if (isSqlExpr(val2)) {
                    buf.append(fmtColumn(key)).append("=").append(StrUtils.decodeSqlExpr(val2)).append(",");
                } else {
                    buf.append(fmtColumn(key)).append("=?,");
                    args.add(value);
                }
            } else {
                buf.append(fmtColumn(key)).append("=?,");
                args.add(value);
            }
        });

        buf.deleteCharAt(buf.length() - 1);
    }

    private void updateItemsBuildByFields0(DataRow data, StringBuilder buf) {
        data.forEach((key, value) -> {
            buf.append(fmtColumn(key)).append("=?,");
        });
        buf.deleteCharAt(buf.length() - 1);
    }


    /**
     * 执行批量合并插入，使用集合数据
     */
    public int[] updateList(List<DataRow> valuesList, String conditionColumns) throws SQLException {
        if (valuesList == null || valuesList.size() == 0) {
            return null;
        }

        return updateList(valuesList.get(0), valuesList, conditionColumns);
    }

    public int[] updateList(List<DataRow> valuesList, IColumn... conditionColumns) throws SQLException {
        return updateList(valuesList, IExpr.getCodes(_context, conditionColumns));
    }

    /**
     * 执行批量合并插入，使用集合数据（由dataBuilder构建数据）
     */
    public <T> int[] updateList(Collection<T> valuesList, Act2<T, DataRow> dataBuilder, String conditionColumns) throws SQLException {
        if (valuesList == null || valuesList.size() == 0) {
            return null;
        }

        List<DataRow> list2 = new ArrayList<>();

        for (T values : valuesList) {
            DataRow item = DataRow.create();
            dataBuilder.run(values, item);

            list2.add(item);
        }


        if (list2.size() > 0) {
            return updateList(list2.get(0), list2, conditionColumns);
        } else {
            return null;
        }
    }

    public <T> int[] updateList(Collection<T> valuesList, Act2<T, DataRow> dataBuilder, IColumn... conditionColumns) throws SQLException {
        return updateList(valuesList, dataBuilder, IExpr.getCodes(_context, conditionColumns));
    }


    protected <T extends GetHandler> int[] updateList(DataRow cols, Collection<T> valuesList, String conditionColumns) throws SQLException {
        if (valuesList == null || valuesList.size() == 0) {
            return null;
        }

        if (cols == null || cols.size() == 0) {
            return null;
        }

        String[] ff = conditionColumns.split(",");

        if (ff.length == 0) {
            throw new RuntimeException("Please enter constraints");
        }


        _builder.backup();

        this.where("1=1");
        for (String f : ff) {
            this.and(f + "=?");
        }

        List<Object[]> argList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        _context.metaData().getDialect().updateCmdBegin(sb, _table.__getTableSpec().name());
        _context.metaData().getDialect().updateCmdSet(sb, _table.__getTableSpec().name());

        updateItemsBuildByFields0(cols, sb);

        for (GetHandler item : valuesList) {
            List<Object> tmp = new ArrayList<>();
            for (String key : cols.keySet()) {
                tmp.add(item.get(key));
            }

            for (String key : ff) {
                tmp.add(item.get(key));
            }
            argList.add(tmp.toArray());
        }


        _builder.insert(sb.toString(), argList.toArray());

        int[] rst = compile().executeBatch();

        _builder.restore();

        return rst;
    }


    /**
     * 执行删除，并返回影响行数
     */
    public int delete() throws SQLException {
        return deleteCompile().execute();
    }

    /**
     * 删除编译并返回命令
     * */
    public DbCommandImpl deleteAsCmd(){
        return deleteCompile().getCommand();
    }

    /**
     * 删除编译
     * */
    protected DbQuery deleteCompile()  {
        StringBuilder sb = new StringBuilder();

        _context.metaData().getDialect().deleteCmd(sb, _table.__getTableSpec().name(), _builder.indexOf(" FROM ") < 0);

        _builder.insert(sb.toString());

        if (limit_top > 0) {
            if (dbType() == DbType.MySQL || dbType() == DbType.MariaDB) {
                _builder.append(" LIMIT ?", limit_top);
            }
        }


        if (WaadConfig.isDeleteMustConditional && _builder.indexOf(" WHERE ") < 0) {
            throw new RuntimeException("Lack of delete condition!!!");
        }

        return compile();
    }


    protected int limit_start, limit_size;

    /**
     * 添加SQL paging语句
     */
    public T limit(int start, int size) {
        limit_start = start;
        limit_size = size;
        //_builder.append(" LIMIT " + start + "," + rows + " ");
        return (T) this;
    }

    protected int fetch_size;
    public T fetchSize(int size) {
        fetch_size = size;
        return (T) this;
    }

    protected int limit_top = 0;

    /**
     * 添加SQL top语句
     */
    public T limit(int size) {
        limit_top = size;
        //_builder.append(" LIMIT " + rows + " ");
        return (T) this;
    }


    /**
     * 添加SQL paging语句
     */
    public T paging(int start, int size) {
        limit_start = start;
        limit_size = size;
        //_builder.append(" LIMIT " + start + "," + rows + " ");
        return (T) this;
    }

    /**
     * 添加SQL top语句
     */
    public T top(int size) {
        limit_top = size;
        //_builder.append(" LIMIT " + rows + " ");
        return (T) this;
    }



    String _hint = null;

    public T hint(String hint) {
        _hint = hint;
        return (T) this;
    }


    public Resultable select(String columns) {
        return selectDo(columns);
    }

    public Resultable select(IExpr... columns) {
        return selectDo(IExpr.getCodes(_context, columns));
    }

    public DbCommandImpl selectAsCmd(String columns){
       return selectCompile(columns).getCommand();
    }

    public DbCommandImpl selectAsCmd(IExpr... columns) {
        return selectCompile(IExpr.getCodes(_context, columns)).getCommand();
    }

    protected Resultable selectDo(String columns) {
        DbQuery rst = selectCompile(columns);

        if (_cache != null) {
            rst.cache(_cache);
        }

        return rst;
    }

    protected DbQuery selectCompile(String columns) {
        return selectCompile(columns, true);
    }

    /**
     * 查询编译
     * @param columns 查询的列
     * @param doFormat 是否对查询的列做格式化
     * @return
     */
    protected DbQuery selectCompile(String columns, boolean doFormat) {
        select_do(columns, doFormat);

        DbQuery rst = compile();

        _builder.restore();

        return rst;
    }

    public boolean selectExists() throws SQLException {
        int bak = limit_top;
        limit(1);
        select_do(" 1 ", false);
        limit(bak);

        DbQuery rst = compile();

        if (_cache != null) {
            rst.cache(_cache);
        }

        _builder.restore();

        return rst.getValue() != null;
    }

    public long selectCount() throws SQLException {
        return selectCount("COUNT(*)");
    }

    public long selectCount(String column) throws SQLException {
        if (column.indexOf("(") < 0) {
            column = "COUNT(" + column + ")";
        }

        //备份
        int limit_start_bak = limit_start;
        int limit_size_bak = limit_size;
        int limit_top_bak = limit_top;
        StringBuilder _orderBy_bak = _orderBy;

        limit_start = 0;
        limit_size = 0;
        limit_top = 0;
        _orderBy = null;

        try {
            if(_hasGroup){
                //有分组时，需要用子表的形式查询
                DbQuery groupQuery = selectCompile("1 as _flag", false);

                _builder.backup();

                TableQuery countQuery = new TableQuery(_context);
                TableQuery table = countQuery.table(" (" + groupQuery.commandText + ") a");
                table._builder.paramS = _builder.paramS;
                return table.selectCount("1");
            }

            return selectDo(column).getVariate().longValue(0L);
        } finally {
            //恢复
            limit_start = limit_start_bak;
            limit_size = limit_size_bak;
            limit_top = limit_top_bak;
            _orderBy = _orderBy_bak;
            if(_hasGroup){
                _builder.restore();
            }
        }
    }

    public long selectCount(IColumn column) throws SQLException {
        return selectCount(column.getCode(_context));
    }

    public Variate selectVariate(String column) throws SQLException {
        return selectDo(column).getVariate();
    }

    public Variate selectVariate(IColumn column) throws SQLException {
        return selectVariate(column.getCode(_context));
    }

    public Object selectValue(String column) throws SQLException {
        return selectDo(column).getValue();
    }

    public Object selectValue(IColumn column) throws SQLException {
        return selectValue(column.getCode(_context));
    }

    public <T> T selectValue(String column, T def) throws SQLException {
        return selectDo(column).getValue(def);
    }

    public <T> T selectValue(IColumn column, T def) throws SQLException {
        return selectValue(column.getCode(_context), def);
    }

    public <T> T selectItem(Class<T> clz, String columns) throws SQLException {
        return selectDo(columns).getItem(clz);
    }

    public <T> T selectItem(Class<T> clz, IExpr... columns) throws SQLException {
        return selectDo(IExpr.getCodes(_context, columns)).getItem(clz);
    }

    public <T> List<T> selectList(Class<T> clz, String columns) throws SQLException {
        return selectDo(columns).getList(clz);
    }

    public <T> List<T> selectList(Class<T> clz, IExpr... columns) throws SQLException {
        return selectDo(IExpr.getCodes(_context, columns)).getList(clz);
    }

    /**
     * @since 2024/06/12
     * */
    public <T> DataReader<T> selectReader(String columns, Class<T> clz) throws SQLException {
        return selectDo(columns).getDataReader(fetch_size).toEntityReader(clz);
    }

    public <T> DataReader<T> selectReader(Class<T> clz, IExpr... columns) throws SQLException {
        return selectReader(IExpr.getCodes(_context, columns), clz);
    }

    public <T> Page<T> selectPage(String columns, Class<T> clz) throws SQLException {
        long total = selectCount();
        List<T> list = selectDo(columns).getList(clz);

        return new PageImpl<>(list, total, limit_size);
    }

    public <T> Page<T> selectPage(Class<T> clz, IExpr... columns) throws SQLException {
        return selectPage(IExpr.getCodes(_context, columns), clz);
    }

    public DataRow selectDataRow(String columns) throws SQLException {
        return selectDo(columns).getDataRow();
    }

    public DataRow selectDataRow(IExpr... columns) throws SQLException {
        return selectDataRow(IExpr.getCodes(_context, columns));
    }

    public DataList selectDataList(String columns) throws SQLException {
        return selectDo(columns).getDataList();
    }

    public DataList selectDataList(IExpr... columns) throws SQLException {
        return selectDataList(IExpr.getCodes(_context, columns));
    }

    /**
     * @since 2024/06/12
     * @since 1.4 (remove fetchSize)
     * */
    public DataReaderForDataRow selectDataReader(String columns) throws SQLException {
        return selectDo(columns).getDataReader(fetch_size);
    }

    public DataReaderForDataRow selectDataReader(IExpr... columns) throws SQLException {
        return selectDataReader(IExpr.getCodes(_context, columns));
    }


    public Page<DataRow> selectDataPage(String columns) throws SQLException {
        long total = selectCount();
        List<DataRow> list = selectDo(columns).getDataList().getRowList();

        return new PageImpl<>(list, total, limit_size);
    }

    public Page<DataRow> selectDataPage(IExpr... columns) throws SQLException {
        return selectDataPage(IExpr.getCodes(_context, columns));
    }

    public Map<String, Object> selectMap(String columns) throws SQLException {
        return selectDo(columns).getMap();
    }

    public Map<String, Object> selectMap(IExpr... columns) throws SQLException {
        return selectMap(IExpr.getCodes(_context, columns));
    }

    public List<Map<String, Object>> selectMapList(String columns) throws SQLException {
        return selectDo(columns).getMapList();
    }

    public List<Map<String, Object>> selectMapList(IExpr... columns) throws SQLException {
        return selectMapList(IExpr.getCodes(_context, columns));
    }

    public Page<Map<String, Object>> selectMapPage(String columns) throws SQLException {
        long total = selectCount();
        List<Map<String, Object>> list = selectDo(columns).getMapList();

        return new PageImpl<>(list, total, limit_size);
    }

    public Page<Map<String, Object>> selectMapPage(IExpr... columns) throws SQLException {
        return selectMapPage(IExpr.getCodes(_context, columns));
    }

    public <T> List<T> selectArray(String column) throws SQLException {
        return selectDo(column).getArray();
    }

    public <T> List<T> selectArray(IColumn column) throws SQLException {
        return selectArray(column.getCode(_context));
    }


    public SelectQ selectQ(String columns) {
        select_do(columns, true);

        return new SelectQ(_builder);
    }

    public SelectQ selectQ(IExpr... columns) {
        return selectQ(IExpr.getCodes(_context, columns));
    }

    private void select_do(String columns, boolean doFormat) {
        _builder.backup();

        //1.构建 xxx... FROM table
        StringBuilder sb = new StringBuilder(_builder.builder.length() + 100);
        sb.append(" ");//不能去掉
        if (doFormat) {
            sb.append(fmtMutColumns(columns)).append(" FROM ").append(_table.__getTableSpec().getCode(_context));
        } else {
            sb.append(columns).append(" FROM ").append(_table.__getTableSpec().getCode(_context));
        }
        sb.append(_builder.builder);

        _builder.builder = sb;

        //2.尝试构建分页
        if (limit_top > 0) {
            _context.metaData().getDialect().buildSelectTopCode(_context, _table_raw, _builder, _orderBy, limit_top);
        } else if (limit_size > 0) {
            _context.metaData().getDialect().buildSelectRangeCode(_context, _table_raw, _builder, _orderBy, limit_start, limit_size);
        } else {
            _builder.insert(0, "SELECT ");
            if (_orderBy != null) {
                _builder.append(_orderBy);
            }
        }

        //3.构建hint
        if (_hint != null) {
            sb.append(_hint);
            _builder.insert(0, _hint);
        }

        //4.构建whith
        if (_builder_bef.length() > 0) {
            _builder.insert(_builder_bef);
        }
    }

    //编译（成DbQuery）
    private DbQuery compile() {
        DbQuery temp = new DbQuery(_context).sql(_builder);

        _builder.clear();

        return temp.onCommandBuilt((cmd) -> {
            cmd.isLog = _isLog;
            cmd.tag = _table.__getTableSpec().name();
        });
    }

    private boolean _usingNull = WaadConfig.isUsingValueNull;
    protected boolean usingNull(){
        return _usingNull;
    }

    /**
     * 充许使用null插入或更新
     */
    public T usingNull(boolean isUsing) {
        _usingNull = isUsing;
        return (T) this;
    }

    private boolean _usingExpr = false;

    protected boolean usingExpr(){
        return _usingExpr;
    }
    /**
     * 充许使用${...}表达式构建sql
     */
    public T usingExpr(boolean isUsing) {
        _usingExpr = isUsing;
        return (T) this;
    }

    private boolean isSqlExpr(String txt) {
        if (_usingExpr == false) {
            return false;
        }

        return StrUtils.isSqlExpr(txt);
    }

    //=======================
    //
    // 缓存控制相关
    //

    protected CacheUsing _cache = null;

    /**
     * 使用一个缓存服务
     */
    public T caching(ICacheService service) {
        _cache = new CacheUsing(service);
        return (T) this;
    }

    /**
     * 是否使用缓存
     */
    public T usingCache(boolean isCache) {
        _cache.usingCache(isCache);
        return (T) this;
    }

    /**
     * 使用缓存时间（单位：秒）
     */
    public T usingCache(int seconds) {
        _cache.usingCache(seconds);
        return (T) this;
    }

    /**
     * 为缓存添加标签
     */
    public T cacheTag(String tag) {
        _cache.cacheTag(tag);
        return (T) this;
    }
}