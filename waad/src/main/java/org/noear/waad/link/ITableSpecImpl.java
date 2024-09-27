package org.noear.waad.link;

import org.noear.waad.utils.StringUtils;

/**
 * 连接表
 *
 * @author noear
 * @since 1.4
 */
public class ITableSpecImpl implements ITableSpec {
    private final String name;
    private final String asName;

    public ITableSpecImpl(String name) {
        this(name, null);
    }

    public ITableSpecImpl(String name, String asName) {
        this.name = name;
        this.asName = asName;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String asName() {
        return asName;
    }

    @Override
    public ITableSpec as(String asName) {
        return new ITableSpecImpl(name, asName);
    }

    @Override
    public String getCode() {
        if (StringUtils.isEmpty(asName)) {
            return name;
        } else {
            return name + " " + asName;
        }
    }
}