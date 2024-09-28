package org.noear.waad.mapper;

import org.noear.waad.DbContext;
import org.noear.waad.DbProcedure;
import org.noear.waad.WaadConfig;
import org.noear.waad.model.Variate;
import org.noear.waad.util.StrUtils;
import org.noear.waad.mapper.xml.XmlSqlBlock;
import org.noear.waad.mapper.xml.XmlSqlFactory;
import org.noear.waad.mapper.xml.XmlSqlLoader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public  class MapperUtil {
    private static final ReentrantLock SYNC_LOCK = new ReentrantLock();

    ///////////////////////////////
    // 代理
    ///////////////////////////////

    /**
     * 生成代理实例
     */
    public static <T> T createProxy(Class<?> mapperInf, DbContext db) {
        XmlSqlLoader.tryLoad();

        return (T) Proxy.newProxyInstance(
                mapperInf.getClassLoader(),
                new Class[]{mapperInf},
                new MapperHandler(db, mapperInf));
    }

    ///////////////////////////////
    // 自动执行
    ///////////////////////////////

    /**
     * @param xsqlid =@{namespace}.{id}
     */
    public static Object exec(DbContext db, String xsqlid, Map<String, Object> paramS, Class<?> rClz, Type rType) throws Exception {
        //3.获取代码块，并检测有效性
        XmlSqlBlock block = XmlSqlFactory.get(xsqlid.substring(1));
        if (block == null) {
            throw new RuntimeException("Xmlsql does not exist:" + xsqlid);
        }

        return exec(db, block, xsqlid, paramS, rClz, rType);
    }

    protected static Object exec(DbContext db, XmlSqlBlock block, String xsqlid, Map<String, Object> paramS, Class<?> rClz, Type rType) throws Exception {

        //4.生成命令
        DbProcedure sp = db.call(xsqlid);
        if (paramS != null) {
            sp.setMap(paramS);
        }

        //5.构建输出
        if (block.isSelect()) {
            //带.说明是有包的；一般会是模型
            if (block._return.indexOf(".") > 0) {
                //实体化处理
                if (block._return_item != null) {
                    //是实体集合
                    if (block._return.indexOf("java.lang.") > 0) {
                        return sp.getArray(0);
                    } else {
                        Class<?> rst_clz2 = item_type(rType, block);

                        return sp.getList(rst_clz2);
                    }
                } else {
                    Class<?> rst_type = rClz;
                    if (rClz == null) {
                        if (StrUtils.isEmpty(block._return) == false) {
                            rst_type = Class.forName(block._return);
                        } else {
                            rst_type = Void.TYPE;
                        }
                    }

                    //是单实体
                    return sp.getItem(rst_type);
                }
            } else {
                if (block._return.startsWith("List<")) {
                    return sp.getDataList().toArray(0);
                }

                if (block._return.startsWith("Set<")) {
                    return sp.getDataList().toSet(0);
                }

                //普通职处理
                switch (block._return) {
                    case "Map":
                        return sp.getMap();
                    case "MapList":
                        return sp.getMapList();
                    case "DataRow":
                        return sp.getDataRow();
                    case "DataList":
                        return sp.getDataList();
                    default: {
                        Variate valV = sp.getVariate();

                        if (valV.getValue() == null) {
                            if (Integer.TYPE == rType) {
                                return WaadConfig.isSelectNullAsDefault ? 0 : null;
                            }

                            if (Long.TYPE == rType) {
                                return WaadConfig.isSelectNullAsDefault ? 0L : null;
                            }

                            if (Float.TYPE == rType) {
                                return WaadConfig.isSelectNullAsDefault ? 0F : null;
                            }

                            if (Double.TYPE == rType) {
                                return WaadConfig.isSelectNullAsDefault ? 0D : null;
                            }
                        } else {
                            //解决 BigDecimal BigInteger 问题
                            if (block._return.toLowerCase().startsWith("int")) {
                                return valV.intValue(WaadConfig.isSelectNullAsDefault ? 0 : null);
                            }

                            if (block._return.toLowerCase().startsWith("long")) {
                                return valV.longValue(WaadConfig.isSelectNullAsDefault ? 0L : null);
                            }

                            if (block._return.toLowerCase().startsWith("float")) {
                                return valV.floatValue(WaadConfig.isSelectNullAsDefault ? 0F : null);
                            }

                            if (block._return.toLowerCase().startsWith("double")) {
                                return valV.doubleValue(WaadConfig.isSelectNullAsDefault ? 0D : null);
                            }

                            if (block._return.toLowerCase().startsWith("str")) {
                                return valV.stringValue(null);
                            }
                        }

                        return valV.getValue();
                    }
                }
            }
        } else {
            if (block.isInsert()) {
                long rst = sp.insert();
                if (block._return != null && block._return.indexOf("ool") > 0) {
                    //说明是bool 或  Boolean
                    return rst > 0;
                } else {
                    return rst;
                }
            } else {
                int rst = sp.execute();
                if (block._return != null && block._return.indexOf("ool") > 0) {
                    //说明是bool 或  Boolean
                    return rst > 0;
                } else {
                    return rst;
                }
            }
        }
    }

    private static Class<?> item_type(Type rType, XmlSqlBlock block) throws Exception {
        if (rType instanceof ParameterizedType) {
            return (Class<?>) (((ParameterizedType) rType).getActualTypeArguments()[0]);
        }

        if (StrUtils.isEmpty(block._return_item) == false) {
            return getClass(block._return_item);
        }

        return null;
    }

    private static Map<String, Class<?>> _clzMap = new HashMap<>();

    private static Class<?> getClass(String fullname) throws Exception {
        Class<?> tmp = _clzMap.get(fullname);
        if (tmp == null) {
            SYNC_LOCK.lock();
            try {
                tmp = _clzMap.get(fullname);
                if (tmp == null) {
                    tmp = Class.forName(fullname);
                    _clzMap.put(fullname, tmp);
                }
            } finally {
                SYNC_LOCK.unlock();
            }
        }

        return tmp;
    }
}