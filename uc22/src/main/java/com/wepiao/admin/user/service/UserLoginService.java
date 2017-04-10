package com.wepiao.admin.user.service;

import java.util.List;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.UserLoginReq;
import com.wepiao.admin.user.rest.msg.UserLoginRes;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface UserLoginService {

    /**
     * 通过手机号和密码登录
     * @param req
     * @param deviceinfoList
     * @throws BaseRestException
     *
     */
    public UserLoginRes login(UserLoginReq req, List<DeviceInfo> deviceinfoList) throws BaseRestException;

}
