package org.noear.waad.mapper.xml;

import org.noear.waad.core.SQLBuilder;

import java.util.Map;

public interface XmlSqlBuilder {
    SQLBuilder build(Map map) throws Exception;
}
