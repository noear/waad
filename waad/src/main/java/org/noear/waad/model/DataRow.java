package org.noear.waad.model;

import org.noear.waad.utils.fun.GetHandler;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 数据行
 *
 * Created by noear on 15/9/2.
 *
 * IDataRow 是为跨平台设计的接口，不能去掉
 */
public interface DataRow extends Map<String,Object>, GetHandler, Serializable {
    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    DataRow set(String name, Object value);

    /////////////////////////////////

    DataRow setIf(boolean condition, String name, Object value);

    DataRow setDf(String name, Object value, Object def);

    DataRow setMap(Map<String, Object> data);

    DataRow setMapIf(Map<String, Object> data, BiFunction<String, Object, Boolean> condition);

    DataRow setEntity(Object obj);

    DataRow setEntityIf(Object obj, BiFunction<String, Object, Boolean> condition);

    <T> T toEntity(Class<T> cls);

    ////////////

    Object get(int index);

    Variate getVariate(String name);

    Short getShort(String name);

    Integer getInt(String name);

    Long getLong(String name);

    Float getFloat(String name);

    Double getDouble(String name);

    BigDecimal getBigDecimal(String name);

    BigInteger getBigInteger(String name);

    String getString(String name);

    Boolean getBoolean(String name);

    Date getDateTime(String name);

    LocalDateTime getLocalDateTime(String name);

    LocalDate getLocalDate(String name);

    LocalTime getLocalTime(String name);


    default Map<String, Object> getMap() {
        return this;
    }

    static DataRow create() {
        return new DataRowImpl();
    }
}
