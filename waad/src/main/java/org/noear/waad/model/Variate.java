package org.noear.waad.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by noear on 14-6-12.
 */
public interface Variate extends Serializable {
    boolean isNull();

    String getName();
    Object getValue();

    Object value();
    <T> T value(T def);

    Double doubleValue(Double def);
    default Double doubleValue(){
        return doubleValue(null);
    }

    Float floatValue(Float def);
    default Float floatValue(){
        return floatValue(null);
    }

    Long longValue(Long def);
    default Long longValue(){
        return longValue(null);
    }

    Integer intValue(Integer def);
    default Integer intValue(){
        return intValue(null);
    }

    String stringValue(String def);
    default String stringValue(){
        return stringValue(null);
    }

    Boolean boolValue(Boolean def);
    default Boolean boolValue(){
        return boolValue(null);
    }

    Date dateValue(Date def);
    default Date dateValue(){
        return dateValue(null);
    }


    ////////////////////////////////

    static Variate create(){
        return new VariateImpl();
    }

    static Variate create(String name, Object value){
        return new VariateImpl(name, value);
    }
}
