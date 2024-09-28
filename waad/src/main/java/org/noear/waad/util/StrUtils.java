package org.noear.waad.util;

/**
 * 字符串工具
 *
 * @author noear
 * @since 3.0
 * */
public class StrUtils {
    /**
     * 是否为空
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !(str == null || str.length() == 0);
    }

    /**
     * 是否为SQL表达式
     */
    public static boolean isSqlExpr(String txt) {
        if (txt.startsWith("${") && txt.endsWith("}")
                && txt.indexOf(" ") < 0
                && txt.length() < 100) { //不能出现空隔，且100字符以内。否则视为普通字符串值
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解码SQL表达式
     */
    public static String decodeSqlExpr(String txt) {
        return txt.substring(2, txt.length() - 1);
    }
}