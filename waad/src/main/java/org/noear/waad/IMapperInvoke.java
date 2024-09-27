package org.noear.waad;

import org.noear.waad.wrap.MethodWrap;

public interface IMapperInvoke {
    Object call(Object proxy, DbContext db, String sqlid, Class<?> caller, MethodWrap mWrap, Object[] args) throws Throwable;
}
