package com.sxk.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sxk.constants.LogMsg;

/**
 * 用户中心接入某种内部业务模块（如营销和订单），用以区分其业务类型
 * @author JinSong
 *
 */
public enum InternalServiceType {

    /**
     * 订单类业务
     */
    ORDER(1),
    /**
     * 营销类业务
     */
    MARKETING(2);

    private static final Logger logger = LoggerFactory.getLogger(InternalServiceType.class);

    private int                 serviceTypeIntVal;

    private InternalServiceType(int deviceIdTypeIntVal) {
        this.serviceTypeIntVal = deviceIdTypeIntVal;
    }

    public int getIntVal() {
        return this.serviceTypeIntVal;
    }

    public static InternalServiceType parseInt(int enumVal) {
        InternalServiceType[] enums = InternalServiceType.class.getEnumConstants();
        for (InternalServiceType serviceType : enums) {
            if (serviceType.getIntVal() == enumVal) {
                return serviceType;
            }
        }
        logger.warn(LogMsg.ERR_INTERNAL_SERVICE_TYPE, enumVal);
        throw new IllegalArgumentException("错误的InternalServiceType枚举值：" + enumVal + ",请核对" + InternalServiceType.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        return String.valueOf(this.serviceTypeIntVal);
    }
}
