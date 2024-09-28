package org.noear.waad.util;

import org.noear.waad.model.DataRow;
import org.noear.waad.util.function.Act2;
import org.noear.waad.wrap.ClassWrap;

/**
 * 实体工具
 *
 * @author noear
 * @since 3.0
 * */
public class EntityUtils {
    public static void fromEntity(Object obj, Act2<String, Object> setter) {
        ClassWrap.get(obj.getClass()).fromEntity(obj, setter);
    }

    public static <T> T toEntity(Class<T> clz, DataRow data) {
        return ClassWrap.get(clz).toEntity(data);
    }
}
