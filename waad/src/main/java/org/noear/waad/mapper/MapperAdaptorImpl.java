package org.noear.waad.mapper;

import org.noear.waad.DbContext;
import org.noear.waad.DbProcedure;

import java.util.Map;

/**
 * @author noear
 * @since 1.1
 */
public class MapperAdaptorImpl implements MapperAdaptor {
    static final String hint = "To use the mapper feature, use the 'waad-mapper' dependency package";

    private MapperAdaptor real;

    public MapperAdaptorImpl() {
        try {
            Class<?> clz = MapperAdaptor.class.getClassLoader().loadClass("org.noear.waad.mapper.impl.MapperAdaptorPlusImpl");
            real = (MapperAdaptor) clz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {

        }
    }

    @Override
    public DbProcedure createXmlProcedure(DbContext db, String process, Map<String, Object> args) {
        if (real == null) {
            throw new UnsupportedOperationException(hint);
        } else {
            return real.createXmlProcedure(db, process, args);
        }
    }

    @Override
    public <T> BaseMapper<T> createMapperBase(DbContext db, Class<T> clz, String tableName) {
        if (real == null) {
            throw new UnsupportedOperationException(hint);
        } else {
            return real.createMapperBase(db, clz, tableName);
        }
    }

    @Override
    public <T> T createMapper(DbContext db, Class<T> clz) {
        if (real == null) {
            throw new UnsupportedOperationException(hint);
        } else {
            return real.createMapper(db, clz);
        }
    }

    @Override
    public <T> T createMapper(DbContext db, String xsqlid, Map<String, Object> args) throws Exception {
        if (real == null) {
            throw new UnsupportedOperationException(hint);
        } else {
            return real.createMapper(db, xsqlid, args);
        }
    }
}
