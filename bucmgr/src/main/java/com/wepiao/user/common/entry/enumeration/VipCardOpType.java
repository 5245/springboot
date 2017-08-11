package com.wepiao.user.common.entry.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.user.common.constant.LogMsg;

/**
 * 
 * @description   折扣卡操作类型
 * @author sxk
 * @date 2016年11月21日
 *
 */
public enum VipCardOpType {
    /**
     * 解绑折扣卡
     */
    UNBIND(1),
    /**
     * 折扣卡重新激活
     */
    REACTIVATE(2);

    private static final Logger logger = LoggerFactory.getLogger(VipCardOpType.class);

    private int                 opTypeIntVal;

    private VipCardOpType(int opTypeIntVal) {
        this.opTypeIntVal = opTypeIntVal;
    }

    public int getIntVal() {
        return this.opTypeIntVal;
    }

    public static VipCardOpType parseInt(int enumVal) {
        VipCardOpType[] enums = VipCardOpType.class.getEnumConstants();
        for (VipCardOpType idType : enums) {
            if (idType.getIntVal() == enumVal) {
                return idType;
            }
        }
        logger.warn(LogMsg.ERR_OP_TYPE, enumVal);
        throw new IllegalArgumentException("错误的OpType枚举值：" + enumVal + ",请核对" + VipCardOpType.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        return String.valueOf(this.opTypeIntVal);
    }
}
