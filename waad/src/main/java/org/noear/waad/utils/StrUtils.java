package org.noear.waad.utils;

public class StrUtils {
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !(str == null || str.length() == 0);
    }

    public static boolean isSqlExpr(String txt) {
        if (txt.startsWith("${") && txt.endsWith("}")
                && txt.indexOf(" ") < 0
                && txt.length() < 100) { //不能出现空隔，且100字符以内。否则视为普通字符串值
            return true;
        } else {
            return false;
        }
    }

    public static String decodeSqlExpr(String txt) {
        return txt.substring(2, txt.length() - 1);
    }
}