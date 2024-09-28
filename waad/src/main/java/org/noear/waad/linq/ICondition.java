package org.noear.waad.linq;

import org.noear.waad.DbContext;
import org.noear.waad.core.SQLBuilder;

/**
 * 条件
 *
 * @author noear
 * @since 4.0
 */
public interface ICondition {

    /**
     * 获取代码
     */
    void write(DbContext db, SQLBuilder buf);
}
