package org.noear.waad.mapper;

import org.noear.waad.DbContext;
import org.noear.waad.DbProcedure;

import java.util.Map;

/**
 * Mapper 能力适配器
 *
 * @author noear
 * @since 1.1
 */
public interface IMapperAdaptor {
    /**
     * 生成 xml 处理器
     */
    DbProcedure createXmlProcedure(DbContext db, String process, Map<String, Object> args);

    /**
     * 生成  BaseMapper
     */
    <T> BaseMapper<T> createMapperBase(DbContext db, Class<T> clz, String tableName);

    /**
     * 生成  Mapper
     */
    <T> T createMapper(DbContext db, Class<T> clz);

    /**
     * 生成  Mapper
     */
    <T> T createMapper(DbContext db, String xsqlid, Map<String, Object> args) throws Exception;
}
