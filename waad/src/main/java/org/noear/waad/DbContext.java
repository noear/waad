package org.noear.waad;

import org.noear.waad.core.DbCommand;
import org.noear.waad.core.Events;
import org.noear.waad.core.SQLBuilder;
import org.noear.waad.util.SimpleDataSource;
import org.noear.waad.linq.ITable;
import org.noear.waad.mapper.BaseMapper;
import org.noear.waad.util.RunUtils;
import org.noear.waad.util.StrUtils;
import org.noear.waad.util.function.Act1;
import org.noear.waad.wrap.DbFormater;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by noear on 14-6-12.
 * 数据库上下文
 */
public class DbContext implements Closeable {
    /**
     * 最后次执行命令 (线程不安全，仅供调试用)
     */
    @Deprecated
    public DbCommand lastCommand;
    /**
     * 充许多片段执行
     */
    private boolean allowMultiQueries;

    public boolean isAllowMultiQueries() {
        return allowMultiQueries;
    }

    public void setAllowMultiQueries(boolean allowMultiQueries) {
        this.allowMultiQueries = allowMultiQueries;
    }


    private final Events events = new Events(WaadConfig.globalEvents());

    public Events events() {
        return events;
    }

    private final DbContextMetaData metaData = new DbContextMetaData();

    /**
     * 元信息
     */
    public DbContextMetaData metaData() {
        return metaData;
    }


    /**
     * 获取链接
     */
    public Connection getConnection() throws SQLException {
        return metaData().getConnection();
    }


    //数据集名称
    protected DbFormater _formater = new DbFormater(this);

    //特性支持
    protected Map<String, String> _attrMap = new HashMap<>();

    //代码注解
    protected String _codeHint = null;
    protected String _name;


    /**
     * 名字获取
     */
    public String name() {
        return _name;
    }

    public DbContext name(String name) {
        _name = name;
        WaadConfig.libOfDb.put(name, this);
        return this;
    }

    /**
     * 使用一个有名字的实例
     */
    public static DbContext use(String name) {
        return WaadConfig.libOfDb.get(name);
    }

    //
    // 构建函数 end
    //

    /**
     * 特性设置
     */
    public DbContext attrSet(String name, String value) {
        _attrMap.put(name, value);
        return this;
    }

    /**
     * 特性获取
     */
    public String attr(String name) {
        return _attrMap.get(name);
    }


    /**
     * 代码注解设置
     */
    public DbContext codeHint(String hint) {
        _codeHint = hint;
        return this;
    }

    /**
     * 代码注解获取
     */
    public String codeHint() {
        return _codeHint;
    }


    //
    // 格式化处理
    //
    public DbContext formaterSet(DbFormater formater) {
        _formater = formater;
        return this;
    }

    public DbFormater formater() {
        return _formater;
    }

    //
    // 构建函数 start
    //
    public DbContext(DataSource dataSource) {
        metaData().setDataSource(dataSource);
    }

    public DbContext(Properties prop) {
        String url = prop.getProperty("url");
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");
        String driverClassName = prop.getProperty("driverClassName");

        if (StrUtils.isEmpty(url) || url.startsWith("jdbc:") == false) {
            throw new IllegalArgumentException("DataSource url configuration error");
        }

        if (StrUtils.isNotEmpty(driverClassName)) {
            RunUtils.runTry(() -> Class.forName(driverClassName));
        }

        metaData().setDataSource(new SimpleDataSource(url, username, password));
    }


    //基于线程池配置（如："proxool."）
    public DbContext(String url) {
        this(url, null, null);
    }

    //基于手动配置（无线程池）
    public DbContext(String url, String username, String password) {
        metaData().setDataSource(new SimpleDataSource(url, username, password));
    }

    //
    // 执行能力支持
    //

    public <T> BaseMapper<T> mapperBase(Class<T> clz) {
        return WaadConfig.mapperAdaptor().createMapperBase(this, clz, null);
    }

    public <T> BaseMapper<T> mapperBase(Class<T> clz, String tableName) {
        return WaadConfig.mapperAdaptor().createMapperBase(this, clz, tableName);
    }

    private Map<Class<?>, Object> _mapperMap = new HashMap<>();

    /**
     * 印映一个接口代理
     */
    public <T> T mapper(Class<T> clz) {
        Object tmp = _mapperMap.get(clz);
        if (tmp == null) {
            metaData.SYNC_LOCK.tryLock();
            try {
                tmp = _mapperMap.get(clz);
                if (tmp == null) {
                    tmp = WaadConfig.mapperAdaptor().createMapper(this, clz);
                    _mapperMap.put(clz, tmp);
                }
            } finally {
                metaData.SYNC_LOCK.unlock();
            }
        }

        return (T) tmp;
    }

    /**
     * 印映一份数据
     *
     * @param xsqlid @{namespace}.{id}
     */
    public <T> T mapper(String xsqlid, Map<String, Object> args) throws Exception {
        return (T) WaadConfig.mapperAdaptor().createMapper(this, xsqlid, args);
    }


    /**
     * 获取一个表对象［用于操作插入也更新］
     */
    public TableQuery table(String table) {
        return new TableQuery(this).table(table);
    }

    public TableQuery table(ITable table) {
        return new TableQuery(this).table(table);
    }

    /**
     * 输入process name，获取process执行对象
     *
     * @param process process name,process code,xsqlid
     */
    public DbProcedure call(String process) {
        if (process.startsWith("@")) {
            return WaadConfig.mapperAdaptor().createXmlProcedure(this, process, null);
        }

        return new DbStoredProcedure(this).call(process);
    }

    /**
     * 输入process name，获取process执行对象
     *
     * @param process process name,process code,xsqlid
     */
    public DbProcedure call(String process, Map<String, Object> args) {
        if (process.startsWith("@")) {
            return WaadConfig.mapperAdaptor().createXmlProcedure(this, process, args);
        }

        return new DbStoredProcedure(this).call(process).setMap(args);
    }


    /**
     * 输入SQL，获取查询器
     */
    public DbQuery sql(String code, Object... args) {
        return sql(new SQLBuilder().append(code, args));
    }

    /**
     * 输入SQL builder，获取查询器
     */
    public DbQuery sql(Act1<SQLBuilder> buildRuner) {
        SQLBuilder sql = new SQLBuilder();
        buildRuner.run(sql);
        return sql(sql);
    }

    /**
     * 输入SQL builder，获取查询器
     */
    public DbQuery sql(SQLBuilder sqlBuilder) {
        return new DbQuery(this).sql(sqlBuilder);
    }


    /**
     * 执行代码，按需返回
     */
    public Object exe(String code, Object... args) throws Exception {
        String cmd = "val";
        String[] ss = code.split("::");
        if (ss.length > 1) {
            cmd = ss[0];
            code = ss[1];
        }

        String codeUp = code.trim().substring(0, 10).toUpperCase();
        if (codeUp.startsWith("SELECT ")) {
            switch (cmd) {
                case "obj":
                case "map":
                    return sql(code, args).getMap();

                case "ary":
                case "list":
                    return sql(code, args).getMapList();

                default:
                    return sql(code, args).getValue();
            }
        } else {
            return sql(code, args).execute();
        }
    }

    /**
     * 批量执行
     */
    public int[] exeBatch(String code, List<Object[]> args) throws Exception {
        SQLBuilder sql = new SQLBuilder();
        sql.append(code, args.toArray());
        return sql(sql).executeBatch();
    }

    @Override
    public void close() throws IOException {
        if (metaData != null) {
            metaData.close();
            _mapperMap.clear();
        }
    }
}