package com.wepiao.user.common.util;

public class UrlUtils {
	
	private static final String HTTP_HEADER = "http://";
	
	private static final String HTTPS_HEADER = "https://";
	
	public static String convertToHttps(String str) {
        if (!StringUtil.isEmpty(str) && str.toLowerCase().startsWith(HTTP_HEADER)) {
            return str.replaceFirst(HTTP_HEADER, HTTPS_HEADER);
        }
        return str;
    }
}
