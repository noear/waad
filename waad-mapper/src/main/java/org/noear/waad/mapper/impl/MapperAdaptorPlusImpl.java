package org.noear.waad.mapper.impl;

import org.noear.waad.mapper.BaseMapper;
import org.noear.waad.DbContext;
import org.noear.waad.DbProcedure;
import org.noear.waad.mapper.MapperAdaptor;
import org.noear.waad.mapper.xml.DbXmlSqlProcedure;
import org.noear.waad.mapper.xml.XmlSqlLoader;

import java.util.Map;

/**
 * @author noear
 * @since 1.1
 */
public class MapperAdaptorPlusImpl implements MapperAdaptor {
    @Override
    public DbProcedure createXmlProcedure(DbContext db, String process, Map<String, Object> args) {
        XmlSqlLoader.tryLoad();
        return new DbXmlSqlProcedure(db).sql(process.substring(1)).setMap(args);
    }

    @Override
    public <T> BaseMapper<T> createMapperBase(DbContext db, Class<T> clz, String tableName) {
        return new BaseMapperWrap<T>(db, clz, tableName);
    }


    /**
     * 印映一个接口代理
     */
    public <T> T createMapper(DbContext db, Class<T> clz) {
        return MapperUtil.createProxy(clz, db);
    }

    /**
     * 印映一份数据
     *
     * @param xsqlid @{namespace}.{id}
     */
    public <T> T createMapper(DbContext db, String xsqlid, Map<String, Object> args) throws Exception {
        return (T) MapperUtil.exec(db, xsqlid, args, null, null);
    }
}
