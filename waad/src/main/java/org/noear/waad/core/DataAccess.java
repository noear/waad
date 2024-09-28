package org.noear.waad.core;

import org.noear.waad.*;
import org.noear.waad.cache.CacheUsing;
import org.noear.waad.cache.ICacheService;
import org.noear.waad.cache.Cacheable;
import org.noear.waad.model.DataList;
import org.noear.waad.model.DataRow;
import org.noear.waad.model.DataReaderForDataRow;
import org.noear.waad.model.Variate;
import org.noear.waad.utils.fun.Act1;
import org.noear.waad.utils.fun.Act2;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by noear on 14-9-5.
 * 数据库方问基类
 */
public abstract class DataAccess<T extends DataAccess> implements Cacheable, Resultable,Serializable {
    /*查询语句*/
    public String commandText = null;

    /*数据库上下文*/
    public DbContext context;
    /*访问参数*/
    public List<Object> paramS = new ArrayList<>();

    /*获取执行命令（由子类实现）*/
    protected abstract CommandImpl getCommand() throws SQLException;

    /*获取访问标识（由子类实现）*/
    protected abstract String getCommandID();

    private Act1<CommandImpl> onCommandExpr = null;

    public T onCommandBuilt(Act1<CommandImpl> expr) {
        this.onCommandExpr = expr;
        return (T) this;
    }

    protected void runCommandBuiltEvent(CommandImpl cmd) {
        cmd.isLog = _isLog;

        if (onCommandExpr != null) {
            onCommandExpr.run(cmd);
        }

        //全局监听
        cmd.context.events().runCommandBuiltEvent(cmd);
    }


    public DataAccess(DbContext context) {
        this.context = context;
    }


    /*IWaadKey begin*/
    protected String _waadKey;

    @Override
    public String getWaadKey() {
        return buildWaadKey(paramS);
    }

    protected String buildWaadKey(Collection<Object> args) {
        if (_waadKey == null) {
            StringBuilder sb = new StringBuilder();

            sb.append(getCommandID()).append(":");

            for (Object p : args) {
                sb.append("_").append(p);
            }

            _waadKey = sb.toString();
        }
        return _waadKey;
    }
    /*IWaadKey end*/

    /*设置参数值*/
    protected void doSet(String name, Object value) {
        paramS.add(value);
    }


    private int _isLog;

    public T log(boolean isLog) {
        _isLog = isLog ? 1 : -1;
        return (T) this;
    }
    //=======================
    //
    // 执行相关代码
    //

    /**
     * 执行插入（返回自增ID）
     */
    public long insert() throws SQLException {
        CommandImpl cmd = getCommand();
        return new SQLer(cmd).insert();
    }

    /**
     * 执行更新（返回受影响数）
     */
    public int update() throws SQLException {
        return execute();
    }

    /**
     * 执行删除（返回受影响数）
     */
    public int delete() throws SQLException {
        return execute();
    }

    /**
     * 执行命令（返回受影响数）
     */
    public int execute() throws SQLException {
        CommandImpl cmd = getCommand();
        return new SQLer(cmd).execute();
    }

    /**
     * 批量执行命令（返回受影响数）
     * */
    public int[] executeBatch() throws SQLException {
        CommandImpl cmd = getCommand();
        cmd.isBatch = true;
        return new SQLer(cmd).executeBatch();
    }

    @Override
    public long getCount() throws SQLException {
        return getVariate().longValue(0L);
    }

    @Override
    public Object getValue() throws SQLException {
        return getVariate().getValue();
    }

    /*执行命令（返回符合条件的第一个值）*/
    @Override
    public <T> T getValue(T def) throws SQLException {
        return getVariate().value(def);
    }

    /*执行命令（返回符合条件的第一个值）*/
    @Override
    public Variate getVariate() throws SQLException {
        return getVariate(null);
    }

    /*执行命令（返回符合条件的第一个值）*/
    @Override
    public Variate getVariate(Act2<CacheUsing, Variate> cacheCondition) throws SQLException {
        Variate rst;
        CommandImpl cmd = getCommand();

        if (_cache == null) {
            rst = new SQLer(cmd).getVariate();
        } else {
            _cache.usingCache(cacheCondition);
            rst = _cache.getEx(this.getWaadKey(), Variate.class, () -> (new SQLer(cmd).getVariate()));
        }
        if (rst == null) {
            return Variate.create();
        } else {
            return rst;
        }

    }



    @Override
    public <T> List<T> getArray(String column) throws SQLException {
        return getDataList().toArray(column);
    }

    @Override
    public <T> List<T> getArray(int columnIndex) throws SQLException {
        return getDataList().toArray(columnIndex);
    }

    // -->
    @Override
    public <T> List<T> getList(Class<T> cls) throws SQLException {
        return getDataList().toEntityList(cls);
    }

    @Override
    public <T> List<T> getList(Class<T> cls, Act2<CacheUsing, List<T>> cacheCondition) throws SQLException {
        if (cacheCondition == null) {
            return getList(cls);
        }

        AtomicReference _tmp = new AtomicReference();

        DataList list = getDataList((cu, dl) -> {
            _tmp.set( dl.toEntityList(cls));
            cacheCondition.run(cu, (List<T>) _tmp.get());
        });

        if (_tmp.get() == null) {
            //说明是缓存里拿出来的 // 没有经过 cacheCondition 处理
            return list.toEntityList(cls);
        } else {
            return (List<T>) _tmp.get();
        }
    }

    @Override
    public <T> T getItem(Class<T> cls) throws SQLException {
        DataRow item = getDataRow();

        // nullable 处理
        if (item.size() == 0) {
            if (WaadConfig.isSelectNullAsDefault == false) {
                return null;
            }
        }

        return item.toEntity(cls);
    }

    @Override
    public <T> T getItem(Class<T> cls, Act2<CacheUsing, T> cacheCondition) throws SQLException {
        if (cacheCondition == null) {
            return getItem(cls);
        }

        AtomicReference _tmp = new AtomicReference();

        DataRow item = getDataRow((cu, di) -> {
            _tmp.set(di.toEntity(cls));
            cacheCondition.run(cu, (T) _tmp.get());
        });

        // nullable 处理
        if (item.size() == 0) {
            if (WaadConfig.isSelectNullAsDefault == false) {
                return null;
            }
        }

        if (_tmp.get() == null) {
            //说明是缓存里拿出来的 // 没有经过 cacheCondition 处理
            return item.toEntity(cls);
        } else {
            return (T) _tmp.get();
        }
    }
    // <--

    @Override
    public DataList getDataList() throws SQLException {
        return getDataList(null);
    }

    @Override
    public DataList getDataList(Act2<CacheUsing, DataList> cacheCondition) throws SQLException {
        DataList rst;
        CommandImpl cmd = getCommand();

        if (_cache == null) {
            rst = new SQLer(cmd).getTable();
        } else {
            _cache.usingCache(cacheCondition);
            rst = _cache.getEx(this.getWaadKey(), DataList.class, () -> (new SQLer(cmd).getTable()));
        }

        if (rst == null) {
            return DataList.create();
        } else {
            return rst;
        }
    }

    @Override
    public DataReaderForDataRow getDataReader(int fetchSize) throws SQLException {
        CommandImpl cmd = getCommand();
        return new SQLer(cmd).getReader(fetchSize);
    }

    @Override
    public List<Map<String, Object>> getMapList() throws SQLException {
        return getDataList().getMapList();
    }

    @Override
    public DataRow getDataRow() throws SQLException {
        return getDataRow(null);
    }

    @Override
    public DataRow getDataRow(Act2<CacheUsing, DataRow> cacheCondition) throws SQLException {
        DataRow rst;
        CommandImpl cmd = getCommand();

        if (_cache == null) {
            rst = new SQLer(cmd).getRow();
        } else {
            _cache.usingCache(cacheCondition);
            rst = _cache.getEx(this.getWaadKey(), DataRow.class, () -> (new SQLer(cmd).getRow()));
        }

        if (rst == null) {
            return DataRow.create();
        } else {
            return rst;
        }
    }

    @Override
    public Map<String, Object> getMap() throws SQLException {
        return getDataRow().getMap();
    }

    //=======================
    //
    // 缓存控制相关
    //

    protected CacheUsing _cache = null;
    public CacheUsing cacheUsing(){
        return _cache;
    }

    /*引用一个缓存服务*/
    @Override
    public Resultable caching(ICacheService service) {
        _cache = new CacheUsing(service);
        return this;
    }

    /*是否使用缓存*/
    @Override
    public Resultable usingCache(boolean isCache) {
        _cache.usingCache(isCache);
        return this;
    }

    /*使用缓存时间（单位：秒）*/
    @Override
    public Resultable usingCache(int seconds) {
        _cache.usingCache(seconds);
        return this;
    }

    /*添加缓存标签*/
    @Override
    public Resultable cacheTag(String tag) {
        _cache.cacheTag(tag);
        return this;
    }

    public T cache(CacheUsing cacheUsing) {
        _cache = cacheUsing;
        return (T) this;
    }
}
