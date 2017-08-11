package com.wepiao.user.common.util;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return (str == null || str.length() <= 0);
    }

    public static boolean isEmptyCheckTrim(String str) {
        return (str == null || str.trim().length() <= 0);
    }

    public static String filterCRLF(String str) {
        if (null != str && 0 < str.length()) {
            return str.replaceAll("(\r\n|\r|\n|\n\r)", "");
        } else {
            return str;
        }
    }
}
