package com.wepiao.user.common.util;

/**
 * 处理头像的CDN URL
 * @author Jin Song
 *
 */
public class PortraitUtils {
    public final static String getAbsoluteUrl(String photoUrl) {
        String result = null;
        if (null != photoUrl && 0 < photoUrl.length()) {
            if (photoUrl.startsWith("http")) {
                result = photoUrl;
            } else {
                result = "http://appnfs.wepiao.com" + photoUrl;
            }
        } else {
            result = "http://appnfs.wepiao.com/dataImage/photo.png";
        }
        return result;
    }
}
