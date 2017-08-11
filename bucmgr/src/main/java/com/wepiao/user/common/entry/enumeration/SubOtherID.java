package com.wepiao.user.common.entry.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.user.common.constant.LogMsg;

/**
 * 微信用户子类型的枚举类
 * @author JinSong
 *
 */
public enum SubOtherID {
    /**
     * 其它，对于未知类型和非微信的openId使用
     */
    OTHER(0),
    /**
     * 微信第三方服务
     */
    THIRDPARTY(1),
    /**
     * 微信移动应用
     */
    APP(2),
    /**
     * 微信小程序
     */
    MINA(3);

    private static final Logger logger = LoggerFactory.getLogger(SubOtherID.class);

    private int                 subOtherId;

    private SubOtherID(int subOtherId) {
        this.subOtherId = subOtherId;
    }

    public int getIntVal() {
        return this.subOtherId;
    }

    public static SubOtherID parseInt(int enumVal) {
        SubOtherID[] enums = SubOtherID.class.getEnumConstants();
        for (SubOtherID otherId : enums) {
            if (otherId.getIntVal() == enumVal) {
                return otherId;
            }
        }
        logger.warn(LogMsg.ERR_SUB_OTHER_ID_TYPE, enumVal);
        throw new IllegalArgumentException("错误的SubOtherID枚举值：" + enumVal + ",请核对" + SubOtherID.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
    	String result = "";
    	if(this.subOtherId != OTHER.getIntVal()) {
    		result = String.valueOf(this.subOtherId);
    	}
        return result;
    }
}
