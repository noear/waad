package org.noear.waad;

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
 * Created by noear on 15/9/2.
 *
 * IDataItem 是为跨平台设计的接口，不能去掉
 */
public interface IDataItem extends Map<String,Object>, GetHandler, Serializable {

    IDataItem set(String name, Object value);

    IDataItem setIf(boolean condition, String name, Object value);

    IDataItem setDf(String name, Object value, Object def);

    IDataItem setMap(Map<String, Object> data);

    IDataItem setMapIf(Map<String, Object> data, BiFunction<String, Object, Boolean> condition);
    IDataItem setEntity(Object obj);
    IDataItem setEntityIf(Object obj, BiFunction<String, Object, Boolean> condition);

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
}
