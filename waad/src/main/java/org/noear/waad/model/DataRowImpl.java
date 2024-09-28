package org.noear.waad.model;

import org.noear.waad.utils.fun.GetHandler;
import org.noear.waad.utils.EntityUtils;
import org.noear.waad.utils.LinkedCaseInsensitiveMap;
import org.noear.waad.wrap.ClassWrap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Created by noear on 14-9-10.
 *
 * 不能转为继承自Map
 * 否则，嵌入别的引擎时，会变转为不可知的MapAdapter
 */
class DataRowImpl extends LinkedCaseInsensitiveMap<Object> implements DataRow {
    @Override
    public DataRow set(String name, Object value) {
        put(name, value);
        return this;
    }

    @Override
    public DataRow setIf(boolean condition, String name, Object value) {
        if (condition) {
            set(name, value);
        }
        return this;
    }

    @Override
    public DataRow setDf(String name, Object value, Object def) {
        if (value == null) {
            set(name, def);
        } else {
            set(name, value);
        }
        return this;
    }

    @Override
    public Object get(int index) {
        for (Object key : this.keySet()) {
            if (index == 0) {
                return get(key);
            } else {
                index--;
            }
        }
        return null;
    }

    @Override
    public Variate getVariate(String name) {
        if (containsKey(name)) {
            return Variate.create(name, get(name));
        } else {
            return Variate.create(name, null);
        }
    }


    @Override
    public Short getShort(String name) {
        Number tmp = (Number) get(name);
        if (tmp == null) {
            return null;
        } else {
            return tmp.shortValue();
        }
    }

    @Override
    public Integer getInt(String name) {
        Number tmp = (Number) get(name);
        if (tmp == null) {
            return null;
        } else {
            return tmp.intValue();
        }
    }

    @Override
    public Long getLong(String name) {
        Number tmp = (Number) get(name);
        if (tmp == null) {
            return null;
        } else {
            return tmp.longValue();
        }
    }

    @Override
    public Double getDouble(String name) {
        Number tmp = (Number) get(name);
        if (tmp == null) {
            return null;
        } else {
            return tmp.doubleValue();
        }
    }

    @Override
    public BigDecimal getBigDecimal(String name) {
        return (BigDecimal) get(name);
    }

    @Override
    public BigInteger getBigInteger(String name) {
        return (BigInteger) get(name);
    }

    @Override
    public Float getFloat(String name) {
        Number tmp = (Number) get(name);
        if (tmp == null) {
            return null;
        } else {
            return tmp.floatValue();
        }
    }

    @Override
    public String getString(String name) {
        return (String) get(name);
    }

    @Override
    public Boolean getBoolean(String name) {
        return (Boolean) get(name);
    }

    @Override
    public Date getDateTime(String name) {
        return (Date) get(name);
    }

    @Override
    public LocalDateTime getLocalDateTime(String name) {
        java.sql.Timestamp tmp = (java.sql.Timestamp) get(name);

        if (tmp == null) {
            return null;
        } else {
            return tmp.toLocalDateTime();
        }
    }

    @Override
    public LocalDate getLocalDate(String name) {
        java.sql.Date tmp = (java.sql.Date) get(name);

        if (tmp == null) {
            return null;
        } else {
            return tmp.toLocalDate();
        }
    }

    @Override
    public LocalTime getLocalTime(String name) {
        java.sql.Time tmp = (java.sql.Time) get(name);

        if (tmp == null) {
            return null;
        } else {
            return tmp.toLocalTime();
        }
    }

    /**
     * 从map加载数据
     */
    public DataRow setMap(Map<String, Object> data) {
        //
        //保持也where的相同逻辑
        //
        return setMapIf(data, (k, v) -> v != null);
    }

    public DataRow setMapIf(Map<String, Object> data, BiFunction<String, Object, Boolean> condition) {
        data.forEach((k, v) -> {
            if (condition.apply(k, v)) {
                set(k, v);
            }
        });

        return this;
    }

    /**
     * 从Entity 加载数据
     */
    public DataRow setEntity(Object obj) {
        //
        //保持与where的相同逻辑
        //
        return setEntityIf(obj, (k, v) -> v != null);
    }

    public DataRow setEntityIf(Object obj, BiFunction<String, Object, Boolean> condition) {
        EntityUtils.fromEntity(obj, (k, v) -> {
            if (condition.apply(k, v)) {
                set(k, v);
            }
        });
        return this;
    }

    /**
     * 转为Entity
     */
    public <T> T toEntity(Class<T> cls) {
        ClassWrap classWrap = ClassWrap.get(cls);

        return classWrap.toEntity(this);
    }

    //============================

    @Override
    public Object get(String name) {
        return super.get(name);
    }

    //============================

    public static DataRow create(Collection<String> names, GetHandler source) {
        DataRow item = DataRow.create();
        for (String key : names) {
            Object val = source.get(key);
            if (val != null) {
                item.set(key, val);
            }
        }
        return item;
    }
}