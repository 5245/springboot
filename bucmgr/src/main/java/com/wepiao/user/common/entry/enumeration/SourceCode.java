package com.wepiao.user.common.entry.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.user.common.constant.LogMsg;

/**
 * @description address sorce code 收货地址来源
 * @author sxk
 * @date 2016年12月29日
 */
public enum SourceCode {
    //演出
    SHOW(0),
    //商城
    STORE(1),
    //微赛
    WESAI(2),
    //格瓦拉
    GEWARA(3);

    private static final Logger logger = LoggerFactory.getLogger(SourceCode.class);

    private int                 code;

    private SourceCode(int code) {
        this.code = code;
    }

    public int getIntVal() {
        return this.code;
    }

    public static SourceCode parseInt(int enumVal) {
        SourceCode[] enums = SourceCode.class.getEnumConstants();
        for (SourceCode sc : enums) {
            if (sc.getIntVal() == enumVal) {
                return sc;
            }
        }
        logger.warn(LogMsg.ERR_SOURCE_CODE, enumVal);
        throw new IllegalArgumentException("错误的OpType枚举值：" + enumVal + ",请核对" + SourceCode.class.getSimpleName());
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
