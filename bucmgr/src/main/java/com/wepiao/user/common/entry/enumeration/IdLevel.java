package com.wepiao.user.common.entry.enumeration;

/**
 * OtherID的等级，level标号越小，级别越高
 * L1等级最高
 * @author Jin Song
 *
 */
public enum IdLevel {
    L1(1), L2(2), L3(3);
    private int level;

    private IdLevel(int level) {
        this.level = level;
    }

    public int getIntVal() {
        return this.level;
    }
}
