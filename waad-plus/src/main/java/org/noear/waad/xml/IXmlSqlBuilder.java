package org.noear.waad.xml;

import org.noear.waad.SQLBuilder;

import java.util.Map;

public interface IXmlSqlBuilder {
    SQLBuilder build(Map map) throws Exception;
}
