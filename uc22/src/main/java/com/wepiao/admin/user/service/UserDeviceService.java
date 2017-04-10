package com.wepiao.admin.user.service;

import java.util.List;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.rest.msg.UserDeviceAddReq;
import com.wepiao.admin.user.rest.msg.UserDeviceChangeCheckReq;
import com.wepiao.admin.user.rest.msg.UserDeviceChangeCheckRes;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface UserDeviceService {
    /**
     * 验证手机号对应的会员账号是否更换了设备
     * @param req
     * @param deviceinfo
     * @return
     * @throws BaseRestException
     */
    public UserDeviceChangeCheckRes isDeviceChanged(UserDeviceChangeCheckReq req, DeviceInfo deviceInfo) throws BaseRestException;

    /**
     * 更新手机号对应的会员账号的设备号列表
     * @param req
     * @param deviceinfo
     * @return
     * @throws BaseRestException
     */
    public SingleResultRes addDevice(UserDeviceAddReq req, List<DeviceInfo> deviceInfoList) throws BaseRestException;
}
