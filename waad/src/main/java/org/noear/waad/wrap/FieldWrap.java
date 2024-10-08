package org.noear.waad.wrap;

import org.noear.waad.WaadConfig;
import org.noear.waad.annotation.Exclude;
import org.noear.waad.annotation.Column;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Field wrap
 * */
public class FieldWrap {
    public final Field field;
    public final String name;
    public final boolean pk;
    public final boolean exclude;
    public final boolean readonly;

    private Method _getter;
    private Method _setter;

    protected FieldWrap(Class<?> clz, Field f1, boolean isFinal) {
        field = f1;
        exclude = ((f1.getAnnotation(Exclude.class) != null) || Modifier.isFinal(f1.getModifiers()));
        readonly = isFinal;

        Column fn = f1.getAnnotation(Column.class);
        if (fn != null) {
            name = fn.value();
        } else {
            name = WaadConfig.namingStrategy().fieldToColumnName(clz, f1);
        }

        pk = WaadConfig.primaryKeyStrategy().fieldIsPrimaryKey(clz, f1);

        field.setAccessible(true);

        _getter = findGetter(clz, f1);
        _setter = findSetter(clz, f1);
    }

    public Object getValue(Object tObj) throws ReflectiveOperationException {
        if (_getter == null) {
            return field.get(tObj);
        } else {
            return _getter.invoke(tObj);
        }
    }

    public void setValue(Object tObj, Object val) throws ReflectiveOperationException {
        if (readonly) {
            return;
        }

        try {
            if (val == null) {
                return;
            }

            val = WaadConfig.convertStrategy().convert(val, field.getType());

            if (_setter == null) {
                field.set(tObj, val);
            } else {
                _setter.invoke(tObj, new Object[]{val});
            }
        } catch (Exception ex) {
            if (val == null) {
                throw new IllegalArgumentException(name + "(" + field.getType().getSimpleName() + ")类型接收失败!", ex);
            }
            throw new IllegalArgumentException(
                    name + "(" + field.getType().getSimpleName() +
                            ")类型接收失败：val(" + val.getClass().getSimpleName() + ")", ex);
        }
    }

    private static Method findGetter(Class<?> tCls, Field field) {
        String fieldName = field.getName();
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String setMethodName = "get" + firstLetter + fieldName.substring(1);

        try {
            Method getFun = tCls.getMethod(setMethodName);
            if (getFun != null) {
                return getFun;
            }
        } catch (NoSuchMethodException ex) {

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static Method findSetter(Class<?> tCls, Field field) {
        String fieldName = field.getName();
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String setMethodName = "set" + firstLetter + fieldName.substring(1);

        try {
            Method setFun = tCls.getMethod(setMethodName, new Class[]{field.getType()});
            if (setFun != null) {
                return setFun;
            }
        } catch (NoSuchMethodException ex) {

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }
}