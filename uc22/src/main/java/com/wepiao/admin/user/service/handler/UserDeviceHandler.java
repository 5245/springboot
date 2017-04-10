package com.wepiao.admin.user.service.handler;

import java.util.List;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface UserDeviceHandler {
    /**
     * 在用户（App用户或者第三方用户）注册/登录的时将用户的device信息记录下来(只存db)
     */
    public void addUserDevice(String id, OtherID idType, List<DeviceInfo> deviceInfo) throws BaseRestException;

    /**
     * 获取用户最近一次使用的device信息
     */
    public DeviceInfo getLatestUsedUserDevice(String id, OtherID idType) throws BaseRestException;
}
