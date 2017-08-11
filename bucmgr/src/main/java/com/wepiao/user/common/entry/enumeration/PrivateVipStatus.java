/**
 * Project Name:uc-common
 * File Name:PrivateVipStatus.java
 * Package Name:com.wepiao.user.common.entry.enumeration
 * Date:2016年12月8日下午4:35:22
 *
*/

package com.wepiao.user.common.entry.enumeration;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.user.common.constant.LogMsg;

/**
 * ClassName:PrivateVipStatus <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年12月8日 下午4:35:22 <br/>
 * @author   liujie
 * @version  
 * @see 	 
 */
public enum PrivateVipStatus {
    //可用
    AVAILABLE(1),
    //不可用
    UNAVAILABLE(2);
    
    private static final Logger logger = LoggerFactory.getLogger(PrivateVipStatus.class);

    private int status;
    
    private PrivateVipStatus(int status) {
        this.status = status;
    }
    
    public int getIntVal() {
        return this.status;
    }

    public static PrivateVipStatus parseInt(int enumVal) {
        PrivateVipStatus[] enums = PrivateVipStatus.class.getEnumConstants();
        for (PrivateVipStatus idType : enums) {
            if (idType.getIntVal() == enumVal) {
                return idType;
            }
        }
        logger.warn(LogMsg.ERR_OP_TYPE, enumVal);
        throw new IllegalArgumentException("错误的OpType枚举值：" + enumVal + ",请核对" + PrivateVipStatus.class.getSimpleName());
    }

    @Override
    public String toString() {
        return String.valueOf(this.status);
    }
}
