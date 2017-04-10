package com.wepiao.admin.user.service;

import java.util.List;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.MobileRegisterReq;
import com.wepiao.admin.user.rest.msg.MobileRegisterRes;
import com.wepiao.admin.user.rest.msg.OpenIdRegisterReq;
import com.wepiao.admin.user.rest.msg.OpenIdRegisterRes;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface UserRegisterService {
    /**
     * 通过手机号注册用户,手机号已存在抛出错误代码，不存在注册新用户
     * @param req
     * @param deviceinfoList
     * @throws BaseRestException
     */
    public MobileRegisterRes registerMobileUser(MobileRegisterReq req, List<DeviceInfo> deviceinfoList) throws BaseRestException;

    /**
     * 通过第三方（微博、微信、手Q）用户的openId注册用户
     * @param req
     * @param deviceinfoList
     * @throws BaseRestException
     */
    public OpenIdRegisterRes registerThirdPartyUser(OpenIdRegisterReq req, List<DeviceInfo> deviceinfoList) throws BaseRestException;

}
