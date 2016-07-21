package com.sxk.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sxk.constants.LogMsg;

/**
 * 用户标签的枚举类型
 * @author JinSong
 *
 */
public enum UserTagEnum {

    /**
     * 电影老用户
     */
    MOVIE_OLD_USR(0),
    /**
     * 演出老用户
     */
    SHOW_OLD_USR(1);

    private static final Logger logger = LoggerFactory.getLogger(UserTagEnum.class);

    private int                 tagId;

    private UserTagEnum(int tagId) {
        this.tagId = tagId;
    }

    public int getIntVal() {
        return this.tagId;
    }

    public static UserTagEnum parseInt(int enumVal) {
        UserTagEnum[] enums = UserTagEnum.class.getEnumConstants();
        for (UserTagEnum tagId : enums) {
            if (tagId.getIntVal() == enumVal) {
                return tagId;
            }
        }
        logger.warn(LogMsg.ERR_USER_TAG_TYPE, enumVal);
        throw new IllegalArgumentException("错误的用户标签枚举值：" + enumVal + ",请核对" + UserTagEnum.class.getSimpleName());
    }
}
