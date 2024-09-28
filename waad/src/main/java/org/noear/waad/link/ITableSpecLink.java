package org.noear.waad.link;

import org.noear.waad.DbContext;
import org.noear.waad.util.StrUtils;

/**
 * 连接表
 *
 * @author noear
 * @since 4.0
 */
class ITableSpecLink implements ITableSpec {
    private final String name;
    private final String asName;

    protected ITableSpecLink(String name) {
        this(name, null);
    }

    protected ITableSpecLink(String name, String asName) {
        this.name = name;
        this.asName = asName;
    }

    /**
     * 表名
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     * 别名
     */
    @Override
    public String asName() {
        return asName;
    }

    /**
     * 获取代码
     */
    @Override
    public String getCode(DbContext db) {
        if (StrUtils.isEmpty(asName)) {
            return name;
        } else {
            return name + " " + asName;
        }
    }
}