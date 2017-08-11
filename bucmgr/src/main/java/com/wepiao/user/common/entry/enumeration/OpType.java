package com.wepiao.user.common.entry.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.user.common.constant.LogMsg;

/**
 * 会员执行的操作类型
 * @author JinSong
 *
 */
public enum OpType {

    /**
     * 登录
     */
    LOGIN(1),
    /**
     * 修改手机号
     */
    CHANGE_MOBILENO(2),
	/**
     * 绑定手机号
     */
    BIND_MOBILENO(3),
    /**
     * 注册手机号
     */
    REGISTER_MOBILENO(4);

    private static final Logger logger = LoggerFactory.getLogger(OpType.class);

    private int                 opTypeIntVal;

    private OpType(int opTypeIntVal) {
        this.opTypeIntVal = opTypeIntVal;
    }

    public int getIntVal() {
        return this.opTypeIntVal;
    }

    public static OpType parseInt(int enumVal) {
        OpType[] enums = OpType.class.getEnumConstants();
        for (OpType idType : enums) {
            if (idType.getIntVal() == enumVal) {
                return idType;
            }
        }
        logger.warn(LogMsg.ERR_OP_TYPE, enumVal);
        throw new IllegalArgumentException("错误的OpType枚举值：" + enumVal + ",请核对" + OpType.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        return String.valueOf(this.opTypeIntVal);
    }
}
