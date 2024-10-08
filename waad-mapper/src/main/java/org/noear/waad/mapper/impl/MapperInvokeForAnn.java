package org.noear.waad.mapper.impl;

import org.noear.waad.*;
import org.noear.waad.annotation.Sql;
import org.noear.waad.cache.ICacheService;
import org.noear.waad.core.DataAccess;
import org.noear.waad.model.DataRow;
import org.noear.waad.model.DataList;
import org.noear.waad.model.Variate;
import org.noear.waad.util.StrUtils;
import org.noear.waad.wrap.MethodWrap;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapperInvokeForAnn implements MapperInvoke {
    public Object call(Object proxy, DbContext db, String sqlid, Class<?> caller, MethodWrap mWrap, Object[] args) throws Throwable {
        Sql ann = mWrap.method.getAnnotation(Sql.class);

        if(ann == null){
            return MapperHandler.UOE;
        }


        String _sql = ann.value();
        String _caching = ann.caching();
        String _cacheClear = ann.cacheClear();

        //1.获取缓存服务
        ICacheService cache_tmp = null;
        if (StrUtils.isEmpty(_caching) == false) {
            cache_tmp = WaadConfig.libOfCache.get(_caching);

            if (cache_tmp == null) {
                throw new RuntimeException("WaadConfig.libOfCache does not exist:@" + _caching);
            }
        }

        //2.构建参数
        Map<String, Object> _map = new HashMap<>();
        Parameter[] names = mWrap.parameters;
        for (int i = 0, len = names.length; i < len; i++) {
            if (args[i] != null) {
                String key = names[i].getName();
                Object val = args[i];

                //如果是_map参数，则做特殊处理
                if ("_map".equals(key) && val instanceof Map) {
                    _map.putAll((Map<String, Object>) val);
                } else {
                    _map.put(key, val);
                }
            }
        }

        //3.确定sql
        String sqlUp = "# " + _sql.toUpperCase();

        //4.生成访问对象
        DataAccess sp = null;
        if(sqlUp.indexOf("@") > 0) {
            sp = db.call(_sql).setMap(_map);
        }else if(sqlUp.indexOf("?") > 0){
            sp = db.sql(_sql,args);
        }else {
            sp = db.sql(_sql);
        }


        //5.执行
        ICacheService cache = cache_tmp;

        if (sqlUp.indexOf(" DELETE ") > 0 || sqlUp.indexOf(" UPDATE ") > 0) {
            int rst = sp.execute();

            if (cache != null && StrUtils.isEmpty(_cacheClear) == false) {
                Arrays.asList(formatTag(_cacheClear, _map).split(",")).forEach((k) -> {
                    cache.clear(k);
                });
            }

            return rst;
        }

        if (sqlUp.indexOf(" INSERT ") > 0) {
            long rst = sp.insert();

            if (cache != null && StrUtils.isEmpty(_cacheClear) == false) {
                Arrays.asList(formatTag(_cacheClear, _map).split(",")).forEach((k) -> {
                    cache.clear(k);
                });
            }

            return rst;
        }

        if (sqlUp.indexOf(" SELECT ") > 0) {
            //5.构建输出
            return forSelect(sp, _map, mWrap, ann, cache);

        }

        return null;
    }

    private  Object forSelect(DataAccess sp, Map<String,Object> map, MethodWrap mWrap, Sql ann, ICacheService cache) throws Throwable {
        String _cacheTag = ann.cacheTag();
        int    _usingCache = ann.usingCache();

        if(cache!=null) {
            //缓存处理
            //
            sp.caching(cache);

            //缓存时间控制
            if (_usingCache > 0) {
                sp.usingCache(_usingCache);
            }

            //缓存标签处理
            if(StrUtils.isEmpty(_cacheTag) == false) {
                _cacheTag = formatTag(_cacheTag, map);

                if (_cacheTag.indexOf("}") < 0) {
                    Arrays.asList(_cacheTag.split(",")).forEach((k)->{
                        sp.cacheTag(k);
                    });
                }else{
                    String _cacheTag2 = _cacheTag;
                    sp.cacheUsing().usingCache((cu,rst)->{
                        if(rst instanceof DataRow){
                            Arrays.asList(formatTag(_cacheTag2, ((DataRow)rst).getMap()).split(",")).forEach((k)->{
                                sp.cacheTag(k);
                            });
                        }
                    });
                }
            }
        }


        Class<?> rst_type = mWrap.returnType;
        Type rst_type2 = mWrap.returnGenericType;

        String rst_type_str = rst_type.getName();
        String rst_type2_str = null;

        if (DataRow.class.isAssignableFrom(rst_type)) {
            return sp.getDataRow();
        }

        if (DataList.class.isAssignableFrom(rst_type)) {
            return sp.getDataList();
        }

        if (Map.class.isAssignableFrom(rst_type)) {
            return sp.getMap();
        }

        if (Collection.class.isAssignableFrom(rst_type)) {
            //是实体集合
            //
            rst_type2 = ((ParameterizedType) rst_type2).getActualTypeArguments()[0];
            rst_type2_str = rst_type2.getTypeName();

            if (rst_type2_str.startsWith("java.") == false) {
                //
                //list<Model>
                //
                Class<?> rst_clz2 = (Class<?>)rst_type2;
                return sp.getList(rst_clz2);
            } else {
                //list<Map>
                if (rst_type2_str.indexOf("Map") > 0) {
                    return sp.getMapList();
                } else {
                    //list<Object>
                    return sp.getDataList().toArray(0);
                }
            }
        }

        //是单实体
        if (rst_type_str.startsWith("java") == false && rst_type_str.indexOf(".") > 0) {
            return sp.getItem(rst_type);
        }

        Variate val = sp.getVariate();

        if (Long.class == (rst_type) || rst_type == Long.TYPE) {
            return val.longValue(0L);
        }

        if (Integer.class == (rst_type) || rst_type == Integer.TYPE) {
            return val.intValue(0);
        }

        return val.getValue();
    }

    private String formatTag(String tags, Map map) {
        String tags2 = tags;

        Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");
        Matcher m = pattern.matcher(tags);
        while (m.find()) {
            String mark = m.group(0);
            String name = m.group(1);
            if(map.containsKey(name)){
                String val = String.valueOf(map.get(name));

                tags2 = tags2.replace(mark, val);
            }
        }

        return tags2;
    }
}
