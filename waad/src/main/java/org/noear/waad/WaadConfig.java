package org.noear.waad;

import org.noear.waad.cache.ICacheServiceEx;
import org.noear.waad.core.Events;
import org.noear.waad.mapper.IMapperAdaptorImpl;
import org.noear.waad.mapper.IMapperAdaptor;
import org.noear.waad.wrap.PrimaryKeyStrategy;
import org.noear.waad.wrap.NamingStrategy;
import org.noear.waad.wrap.TypeConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by noear on 14/11/20.
 */
public final class WaadConfig {
    public static boolean isUsingValueExpression = true;
    public static boolean isUsingValueNull = false;

    /**
     * 是否使用当前上下文的 schema 替换表达式里的 $
     */
    public static boolean isUpdateMustConditional = true;
    public static boolean isDeleteMustConditional = true;
    public static boolean isUsingUnderlineColumnName = true;
    public static boolean isSelectNullAsDefault = true;

    /**
     * 非注解的命名策略
     */
    public static NamingStrategy namingStrategy = new NamingStrategy();

    /**
     * 非注解的字段主键策略
     */
    public static PrimaryKeyStrategy primaryKeyStrategy = new PrimaryKeyStrategy();

    /**
     * 字段类型转换器
     */
    public static TypeConverter typeConverter = new TypeConverter();

    /**
     * Mapper 适配器
     */
    public static IMapperAdaptor mapperAdaptor = new IMapperAdaptorImpl();

    /**
     * 链接工厂
     */
    public static DbConnectionFactory connectionFactory = new DbConnectionFactory();

    public static Map<String, ICacheServiceEx> libOfCache = new ConcurrentHashMap<>();
    public static Map<String, DbContext> libOfDb = new ConcurrentHashMap<>();

    /**
     * 事件
     */
    private static final Events events = new Events(null);

    public static Events events() {
        return events;
    }
}
