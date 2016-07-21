package com.sxk.enumeration;

/**
 * 手机号绑定状态的枚举类
 * @author JinSong
 *
 */
public enum BindingStatus {
    /**
     * 绑定状态未知
     */
    UNKNOWN(-1),
    /**
     * 未绑定
     */
    UNBOUND(0),
    /**
     * 已绑定
     */
    BOUND(1);

    private int status;

    private BindingStatus(int status) {
        this.status = status;
    }

    public int getIntVal() {
        return this.status;
    }

    public static BindingStatus parseInt(int enumVal) {
        BindingStatus[] enums = BindingStatus.class.getEnumConstants();
        for (BindingStatus stat : enums) {
            if (stat.getIntVal() == enumVal) {
                return stat;
            }
        }
        throw new IllegalArgumentException("错误的绑定状态枚举值：" + enumVal + ",请核对" + BindingStatus.class.getSimpleName());
    }
}
