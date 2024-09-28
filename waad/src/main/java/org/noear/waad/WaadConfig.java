package org.noear.waad;

import org.noear.waad.cache.ICacheServiceEx;
import org.noear.waad.core.Events;
import org.noear.waad.utils.ConnectionStrategy;
import org.noear.waad.mapper.MapperAdaptorImpl;
import org.noear.waad.mapper.MapperAdaptor;
import org.noear.waad.utils.PrimaryKeyStrategy;
import org.noear.waad.utils.NamingStrategy;
import org.noear.waad.wrap.TypeConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局配置
 *
 * @author noear
 * @since 14/11/20.
 * @since 4.0
 */
public final class WaadConfig {
    /**
     * 充许使用null插入或更新
     */
    public static boolean isUsingValueNull = false;
    /**
     * 更新时必须要有条件
     */
    public static boolean isUpdateMustConditional = true;
    /**
     * 删除时必须要有条件
     */
    public static boolean isDeleteMustConditional = true;
    public static boolean isUsingUnderlineColumnName = true;
    /**
     * 查找为 null 时，以默认值返回
     */
    public static boolean isSelectNullAsDefault = false;

    /**
     * 使用编译模式（用于 aot 产生代码时）
     */
    public static boolean isUsingCompilationMode = false;


    private static NamingStrategy namingStrategy = new NamingStrategy();

    /**
     * 配置非注解的命名策略
     */
    public static void namingStrategy(NamingStrategy namingStrategy) {
        assert namingStrategy != null;
        WaadConfig.namingStrategy = namingStrategy;
    }

    /**
     * 非注解的命名策略
     */
    public static NamingStrategy namingStrategy() {
        return namingStrategy;
    }


    private static PrimaryKeyStrategy primaryKeyStrategy = new PrimaryKeyStrategy();

    /**
     * 配置非注解的字段主键策略
     */
    public static void primaryKeyStrategy(PrimaryKeyStrategy primaryKeyStrategy) {
        assert primaryKeyStrategy != null;
        WaadConfig.primaryKeyStrategy = primaryKeyStrategy;
    }

    /**
     * 非注解的字段主键策略
     */
    public static PrimaryKeyStrategy primaryKeyStrategy() {
        return primaryKeyStrategy;
    }


    private static ConnectionStrategy connectionStrategy = new ConnectionStrategy();

    /**
     * 配置连接策略
     */
    public static void connectionStrategy(ConnectionStrategy connectionFactory) {
        assert connectionFactory != null;
        WaadConfig.connectionStrategy = connectionFactory;
    }

    /**
     * 连接策略
     */
    public static ConnectionStrategy connectionStrategy() {
        return connectionStrategy;
    }


    private static TypeConverter typeConverter = new TypeConverter();

    public static void typeConverter(TypeConverter typeConverter) {
        assert typeConverter != null;
        WaadConfig.typeConverter = typeConverter;
    }

    /**
     * 字段类型转换器
     */
    public static TypeConverter typeConverter() {
        return typeConverter;
    }


    private static MapperAdaptor mapperAdaptor = new MapperAdaptorImpl();

    /**
     * 映射适配器
     */
    public static MapperAdaptor mapperAdaptor() {
        return mapperAdaptor;
    }


    private static Events globalEvents = new Events(null);

    /**
     * 全局事件
     */
    public static Events globalEvents() {
        return globalEvents;
    }

    ///////////////// final


    public static final Map<String, ICacheServiceEx> libOfCache = new ConcurrentHashMap<>();
    public static final Map<String, DbContext> libOfDb = new ConcurrentHashMap<>();
}