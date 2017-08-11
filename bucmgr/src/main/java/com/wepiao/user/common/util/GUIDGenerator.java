package com.wepiao.user.common.util;

import java.util.UUID;

/**
 * 生成32位的UUID
 * @author JinSong
 *
 */
public class GUIDGenerator {
    public synchronized static String getGUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
}
