package com.wepiao.user.common.entry.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.user.common.constant.LogMsg;

/**
 * 用户使用的Device标识类型
 * @author JinSong
 *
 */
public enum DeviceIdType {

    /**
     * IOS唯一标识
     */
    IDFA(1),
    /**
     * 移动设备唯一标识
     */
    IMEI(2),
    /**
     * mac地址
     */
    MAC(3),
    /**
     * 安卓设备uuid
     */
    DEVICE_ID(4);

    private static final Logger logger = LoggerFactory.getLogger(DeviceIdType.class);

    private int                 deviceIdTypeIntVal;

    private DeviceIdType(int deviceIdTypeIntVal) {
        this.deviceIdTypeIntVal = deviceIdTypeIntVal;
    }

    public int getIntVal() {
        return this.deviceIdTypeIntVal;
    }

    public static DeviceIdType parseInt(int enumVal) {
        DeviceIdType[] enums = DeviceIdType.class.getEnumConstants();
        for (DeviceIdType idType : enums) {
            if (idType.getIntVal() == enumVal) {
                return idType;
            }
        }
        logger.warn(LogMsg.ERR_DEVICE_ID_TYPE, enumVal);
        throw new IllegalArgumentException("错误的DeviceIdType枚举值：" + enumVal + ",请核对" + DeviceIdType.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        return String.valueOf(this.deviceIdTypeIntVal);
    }
}
