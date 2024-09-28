package org.noear.waad.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by noear on 14-6-12.
 * 数据库访问参数（支持范型）
 */
class VariateImpl implements Variate {
    protected String _name;
    protected Object _value;

    public VariateImpl() {
    }

    public VariateImpl(String name, Object value) {
        this._name = name;
        this._value = value;
    }

    public boolean isNull() {
        return _value == null;
    }

    public String getName() {
        return _name;
    }

    public Object getValue() {
        return _value;
    }

    //--------------------
    public <T> T value(T def) {
        if (_value == null)
            return def;
        else {
            return (T) _value;
        }
    }

    public Object value() {
        return _value;
    }

    public Double doubleValue(Double def) {
        if (_value == null) {
            return def;
        }

        if (_value instanceof Number) {
            return ((Number) _value).doubleValue();
        }

        if (_value instanceof String) {
            return Double.parseDouble((String) _value);
        }

        return def;
    }

    public Float floatValue(Float def) {
        if (_value == null) {
            return def;
        }

        if (_value instanceof Number) {
            return ((Number) _value).floatValue();
        }

        if (_value instanceof String) {
            return Float.parseFloat((String) _value);
        }

        return def;
    }

    public Long longValue(Long def) {
        if (_value == null) {
            return def;
        }

        if (_value instanceof Number) {
            return ((Number) _value).longValue();
        }

        if (_value instanceof Boolean) {
            return ((Boolean) _value) ? 1L : 0L;
        }

        if (_value instanceof Date) {
            return ((Date) _value).getTime();
        }

        if (_value instanceof String) {
            return Long.parseLong((String) _value);
        }

        return def;
    }


    public Integer intValue(Integer def) {
        if (_value == null) {
            return def;
        }

        if (_value instanceof Number) {
            return ((Number) _value).intValue();
        }

        if (_value instanceof Boolean) {
            return ((boolean) _value) ? 1 : 0;
        }

        if (_value instanceof String) {
            return Integer.parseInt((String) _value);
        }

        return def;
    }

    public String stringValue(String def) {
        if (_value == null) {
            return def;
        }

        if (_value instanceof String) {
            return (String) _value;
        } else {
            return _value.toString();
        }
    }

    public Boolean boolValue(Boolean def) {
        if (_value == null) {
            return def;
        }

        if (_value instanceof Number) {
            return ((Number) _value).intValue() > 0;
        } else {
            return (Boolean) _value;
        }
    }

    public Date dateValue(Date def) {
        if (_value == null) {
            return def;
        }

        if (_value instanceof String) {
            return Timestamp.valueOf((String) _value);
        } else if (_value instanceof Long) {
            return new Date((Long) _value);
        } else {
            return (Date) _value;
        }
    }
}