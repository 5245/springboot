package com.wepiao.user.common.util;

import java.util.HashSet;
import java.util.Set;

import com.wepiao.user.common.enumeration.LogType;

public class LogRecordUtil {
    private final static Set<String> userTagSet  = new HashSet<String>();
    private final static Set<String> userInfoSet = new HashSet<String>();
    static {
        // userTagSet 初始化
        userTagSet.add("addusertag");
        userTagSet.add("addusertag/static");
        userTagSet.add("addusertag/dynamic");
        //折扣卡
        userTagSet.add("bindvipcard");
        userTagSet.add("lockvipcardinventory");
        userTagSet.add("releasevipcardinventory");
        userTagSet.add("consumevipcardinventory");
        userTagSet.add("refundvipcardinventory");
        userTagSet.add("unbindvipcard");
        userTagSet.add("activatevipcard");
        //私有会员卡
        userTagSet.add("createprivatevipconfig");
        userTagSet.add("createprivatevip");
        userTagSet.add("removeprivatevip");
        
        //商业化活动资格校验
        userTagSet.add("bigdata/importusertag4movieconsumption");

        // userInfoSet 初始化
        userInfoSet.add("mobileregister");
        userInfoSet.add("openidregister");
        userInfoSet.add("login");
        userInfoSet.add("updateuserinfo");
        userInfoSet.add("bindmobileno");
        userInfoSet.add("bindmobileno4ext");
        userInfoSet.add("updatemobileno");
        userInfoSet.add("updatepassword");
        userInfoSet.add("adddevice");
        userInfoSet.add("fastbindmobileno");
        //收货地址
        userInfoSet.add("addpda");
        userInfoSet.add("removepda");
        userInfoSet.add("updatepda");
        //用户中心-uc-ext
        userInfoSet.add("adduserreceivermobile");
        //ucgwr
        userInfoSet.add("validateMobileNo");
        userInfoSet.add("userLogin");
        userInfoSet.add("updatePasswd");
        userInfoSet.add("identifyMobilePass");
        userInfoSet.add("unbindmobile");
    }

    public static LogType judgeLogType(String pathInfo) {
        if (StringUtil.isEmpty(pathInfo)) {
            return LogType.QUERY;
        } else {
            pathInfo = pathInfo.substring(1);
            if (pathInfo.contains("get") || pathInfo.startsWith("is")) {
                return LogType.QUERY;
            } else {
                if (userTagSet.contains(pathInfo)) {
                    return LogType.USERTAG;
                } else if (userInfoSet.contains(pathInfo)) {
                    return LogType.USERINFO;
                } else {
                    return LogType.QUERY;
                }
            }
        }
    }
}
