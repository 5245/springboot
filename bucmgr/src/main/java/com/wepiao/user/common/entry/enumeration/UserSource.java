package com.wepiao.user.common.entry.enumeration;

import com.wepiao.user.common.constant.LogMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 标识用户来源类型的枚举类
 * @author JinSong
 *
 */
public enum UserSource {
    /**
     * 来自娱票儿
     */
    HISTORY(0),
    /**
     * 来自娱票儿
     */
    YUPIAO(1),
    /**
     * 来自格瓦拉
     */
    GEWARA(2),
    /**
     * 同时在娱票儿和格瓦拉共存的
     */
    YUPIAO_GEWARA(3);

    private static final Logger logger = LoggerFactory.getLogger(UserSource.class);

    private int userSource;

    private UserSource(int userSource) {
        this.userSource = userSource;
    }

    public int getIntVal() {
        return this.userSource;
    }

    public static UserSource parseInt(int enumVal) {
        UserSource[] enums = UserSource.class.getEnumConstants();
        for (UserSource us : enums) {
            if (us.getIntVal() == enumVal) {
                return us;
            }
        }
        logger.warn(LogMsg.ERR_USER_SOURCE_TYPE, enumVal);
        throw new IllegalArgumentException("错误的UserSource枚举值：" + enumVal + ",请核对" + UserSource.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        return String.valueOf(this.userSource);
    }
}
