package org.noear.waad.utils;

public class StrUtils {
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !(str == null || str.length() == 0);
    }
}
