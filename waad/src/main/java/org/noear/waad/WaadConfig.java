package org.noear.waad;

import org.noear.waad.cache.ICacheServiceEx;
import org.noear.waad.core.Events;
import org.noear.waad.datasource.ConnectionFactory;
import org.noear.waad.datasource.SimpleConnectionFactory;
import org.noear.waad.mapper.MapperAdaptorImpl;
import org.noear.waad.mapper.MapperAdaptor;
import org.noear.waad.wrap.PrimaryKeyStrategy;
import org.noear.waad.wrap.NamingStrategy;
import org.noear.waad.wrap.TypeConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局配置
 *
 * @author noear
 * @since 14/11/20.
 */
public final class WaadConfig {
    public static boolean isUsingValueExpression = false;
    public static boolean isUsingValueNull = false;

    /**
     * 是否使用当前上下文的 schema 替换表达式里的 $
     */
    public static boolean isUpdateMustConditional = true;
    public static boolean isDeleteMustConditional = true;
    public static boolean isUsingUnderlineColumnName = true;
    public static boolean isSelectNullAsDefault = false;

    /**
     * 使用编译模式（用于产生代码）
     */
    public static boolean isUsingCompilationMode = false;

    /**
     * 非注解的命名策略
     */
    private static NamingStrategy namingStrategy = new NamingStrategy();

    public static void namingStrategy(NamingStrategy namingStrategy) {
        assert namingStrategy != null;
        WaadConfig.namingStrategy = namingStrategy;
    }

    public static NamingStrategy namingStrategy() {
        return namingStrategy;
    }


    private static PrimaryKeyStrategy primaryKeyStrategy = new PrimaryKeyStrategy();

    public static void namingStrategy(PrimaryKeyStrategy primaryKeyStrategy) {
        assert primaryKeyStrategy != null;
        WaadConfig.primaryKeyStrategy = primaryKeyStrategy;
    }

    /**
     * 非注解的字段主键策略
     */
    public static PrimaryKeyStrategy primaryKeyStrategy() {
        return primaryKeyStrategy;
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


    private static ConnectionFactory connectionFactory = new SimpleConnectionFactory();

    public static void connectionFactory(ConnectionFactory connectionFactory) {
        assert connectionFactory != null;
        WaadConfig.connectionFactory = connectionFactory;
    }

    /**
     * 链接工厂
     */
    public static ConnectionFactory connectionFactory() {
        return connectionFactory;
    }


    private static MapperAdaptor mapperAdaptor = new MapperAdaptorImpl();

    /**
     * 遇射适配器
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