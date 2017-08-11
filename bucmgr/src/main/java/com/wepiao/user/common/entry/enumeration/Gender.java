package com.wepiao.user.common.entry.enumeration;

/**
 * 性别的枚举类
 * @author JinSong
 *
 */
public enum Gender {
    /**
     * 性别未知
     */
    UNKNOWN(0),
    /**
     * 男性
     */
    MALE(1),
    /**
     * 女性
     */
    FEMALE(2);

    private int gender;

    private Gender(int gender) {
        this.gender = gender;
    }

    public int getIntVal() {
        return this.gender;
    }

    public static Gender parseInt(int enumVal) throws IllegalArgumentException {
        Gender[] enums = Gender.class.getEnumConstants();
        for (Gender gender : enums) {
            if (gender.getIntVal() == enumVal) {
                return gender;
            }
        }
        throw new IllegalArgumentException("错误的性别枚举值：" + enumVal + ",请核对" + Gender.class.getSimpleName());
    }
}
