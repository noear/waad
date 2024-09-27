package org.noear.waad;

import org.noear.waad.wrap.DbVarType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by noear on 14-6-12.
 * 数据库访问参数（支持范型）
 */
public class Variate implements Serializable {
    protected String _name;
    protected Object _value;

    public Variate() {
    }

    public Variate(String name, Object value) {
        this._name = name;
        this._value = value;
    }

    public boolean isNull() {
        return _value == null;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }


    private DbVarType _type = DbVarType.Object;

    public DbVarType getType() {
        return _type;
    }

    public Object getValue() {
        return _value;
    }

    public void setValue(Object value) {
        _value = value;
    }

    //--------------------
    public String getString() {
        return (String) _value;
    }

    public Date getDate() {
        return (Date) _value;
    }

    public Boolean getBoolean() {
        return (Boolean) _value;
    }

    public Number getNumber() {
        return (Number) _value;
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

    public double doubleValue(double def) {
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

    public float floatValue(float def) {
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

    public long longValue(long def) {
        if (_value == null) {
            return def;
        }

        if (_value instanceof Number) {
            return ((Number) _value).longValue();
        }

        if (_value instanceof Boolean) {
            return ((boolean) _value) ? 1 : 0;
        }

        if (_value instanceof Date) {
            return ((Date) _value).getTime();
        }

        if (_value instanceof String) {
            return Long.parseLong((String) _value);
        }

        return def;
    }


    public int intValue(int def) {
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
