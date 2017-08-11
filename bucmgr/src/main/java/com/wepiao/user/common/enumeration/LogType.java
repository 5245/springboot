package com.wepiao.user.common.enumeration;

/**
 * 日志业务类型的枚举类
 *
 */
public enum LogType {
    /**
     * 不用记录日志
     */
    QUERY(0),
    /**
     * userTag业务的日志
     */
    USERTAG(1),
    /**
     * userInfo业务的日志
     */
    USERINFO(2);

    private int logTypeCode;

    private LogType(int logTypeCode) {
        this.logTypeCode = logTypeCode;
    }

    public int getIntVal() {
        return this.logTypeCode;
    }

    public static LogType parseInt(int enumVal) throws IllegalArgumentException {
        LogType[] enums = LogType.class.getEnumConstants();
        for (LogType logType : enums) {
            if (logType.getIntVal() == enumVal) {
                return logType;
            }
        }
        throw new IllegalArgumentException("错误的性别枚举值：" + enumVal + ",请核对" + LogType.class.getSimpleName());
    }

}
