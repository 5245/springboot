package com.sxk.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sxk.constants.LogMsg;

/**
 * 第三方用户OtherID的枚举类
 * @author JinSong
 *
 */
public enum OtherID {

    /**
     * 数据迁移用，仅仅作为老用户缓存的key值占位符之一
     */
    LEGACY(0, IdLevel.L3),
    /**
     * 新浪微博
     */
    WEIBO(10, IdLevel.L3),
    /**
     * 微信
     */
    WEIXIN(11, IdLevel.L3),
    /**
     * QQ
     */
    QQ(12, IdLevel.L3),
    /**
     * 手机号
     */
    MOBILE(13, IdLevel.L3),
    /**
     * UID
     */
    UID(20, IdLevel.L1),
    /**
     * UnionID
     */
    UnionID(30, IdLevel.L2),
    /**
     * not internal  外部渠道otherID
     */
    EXTERNAL(22, IdLevel.L3);

    private static final Logger logger = LoggerFactory.getLogger(OtherID.class);

    private int                 otherId;

    private IdLevel             level;

    private OtherID(int otherId, IdLevel level) {
        this.otherId = otherId;
        this.level = level;
    }

    public int getIntVal() {
        return this.otherId;
    }

    public IdLevel getLevel() {
        return this.level;
    }

    public static OtherID parseInt(int enumVal) {
        OtherID[] enums = OtherID.class.getEnumConstants();
        for (OtherID otherId : enums) {
            if (otherId.getIntVal() == enumVal) {
                return otherId;
            }
        }
        logger.warn(LogMsg.ERR_OTHER_ID_TYPE, enumVal);
        throw new IllegalArgumentException("错误的OtherID枚举值：" + enumVal + ",请核对" + OtherID.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        return String.valueOf(this.otherId);
    }

    /**
     * 通过Level比较大小，L1的最大
     * @param another
     * @return
     */
    public int compare(OtherID another) {
        // Note: level标号越小则级别越高
        int thisLevel = this.getLevel().getIntVal();
        int anotherLevel = another.getLevel().getIntVal();
        if (thisLevel > anotherLevel) {
            return -1;
        } else if (thisLevel < anotherLevel) {
            return 1;
        } else {
            return 0;
        }
    }
}
