package com.wepiao.user.common.entry.enumeration;

/**
 * 用户状态的枚举类
 * @author JinSong
 *
 */
public enum VipCardOrderStatus {
    //锁定
    LOCKED(0),
    //释放
    RELEASED(1),
    //消费
    CONSUMED(2),
    //回退
    REFUNDED(3);

    private int status;

    private VipCardOrderStatus(int status) {
        this.status = status;
    }

    public int getIntVal() {
        return this.status;
    }

    public static VipCardOrderStatus parseInt(int enumVal) {
        VipCardOrderStatus[] enums = VipCardOrderStatus.class.getEnumConstants();
        for (VipCardOrderStatus stat : enums) {
            if (stat.getIntVal() == enumVal) {
                return stat;
            }
        }
        throw new IllegalArgumentException("错误的状态枚举值：" + enumVal + ",请核对" + VipCardOrderStatus.class.getSimpleName());
    }
}
