package com.sxk.enumeration;

/**
 * 用户状态的枚举类
 * @author JinSong
 *
 */
public enum Status {
    /**
     * 初始化的用户，默认状态
     */
    INIT(0),
    /**
     * 普通用户
     */
    NORMAL(1),
    /**
     * 伪造的名人马甲号
     */
    PSEUDO_CELEBRITY(2),
    /**
     * 会员用户
     */
    VIP(3),
    /**
     * 黑名单用户
     */
    BLACK(8),
    /**
     * 标记删除的
     */
    DELETED(9);

    private int status;

    private Status(int status) {
        this.status = status;
    }

    public int getIntVal() {
        return this.status;
    }

    public static Status parseInt(int enumVal) {
        Status[] enums = Status.class.getEnumConstants();
        for (Status stat : enums) {
            if (stat.getIntVal() == enumVal) {
                return stat;
            }
        }
        throw new IllegalArgumentException("错误的状态枚举值：" + enumVal + ",请核对" + Status.class.getSimpleName());
    }
}
