package com.wepiao.user.common.entry.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.user.common.constant.LogMsg;

/**
 * @description wechatCity cityType 城市类型
 * @author sxk
 * @date 2016年12月29日
 */
public enum CityType {
    //省
    PROVINCE(1),
    //市
    CITY(2),
    //区
    DISTRICT(3);

    private static final Logger logger = LoggerFactory.getLogger(CityType.class);

    private int                 type;

    private CityType(int type) {
        this.type = type;
    }

    public int getIntVal() {
        return this.type;
    }

    public static CityType parseInt(int enumVal) {
        CityType[] enums = CityType.class.getEnumConstants();
        for (CityType cityTpye : enums) {
            if (cityTpye.getIntVal() == enumVal) {
                return cityTpye;
            }
        }
        logger.warn(LogMsg.ERR_CITY_TYPE, enumVal);
        throw new IllegalArgumentException("错误的OpType枚举值：" + enumVal + ",请核对" + CityType.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        return String.valueOf(this.type);
    }
}
