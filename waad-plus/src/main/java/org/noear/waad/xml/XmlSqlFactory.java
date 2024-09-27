package org.noear.waad.xml;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XmlSqlFactory {
    private static Map<String, XmlSqlBlock> _sqlMap = new ConcurrentHashMap<>();

    public static void register(String sqlid, XmlSqlBlock sqlBlock) {
        _sqlMap.put(sqlid, sqlBlock);
    }

    public static void register(String sqlid, IXmlSqlBuilder xmlSqlBuilder) {
        XmlSqlBlock tmp = _sqlMap.get(sqlid);

        //这个注册，相当于绑定
        if (tmp != null) {
            tmp.builder = xmlSqlBuilder;
        }
    }

    public static XmlSqlBlock get(String sqlid){
        return _sqlMap.get(sqlid);
    }
}
