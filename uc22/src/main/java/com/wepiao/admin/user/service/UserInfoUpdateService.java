package com.wepiao.admin.user.service;

import java.util.Map;

import com.wepiao.admin.user.rest.msg.BindMobileNoReq;
import com.wepiao.admin.user.rest.msg.BindMobileNoRes;
import com.wepiao.admin.user.rest.msg.ChangePasswordReq;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.rest.msg.UnBindOpenIDReq;
import com.wepiao.admin.user.rest.msg.UpdateMobileNoReq;
import com.wepiao.admin.user.rest.msg.UserInfoUpdateRes;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface UserInfoUpdateService {
    /**
     * 根据用户的uid修改用户的信息
     * @param reqId
     * @param reqMap
     * @return
     * @throws BaseRestException
     *
     */
    public UserInfoUpdateRes updateUserInfo(String reqId, Map<String, Object> reqMap) throws BaseRestException;

    /**
     * 第三方用户绑定手机号
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public BindMobileNoRes bindMobileNo(BindMobileNoReq req) throws BaseRestException;

    /**
     * 根据uid或extUid更改用户手机号
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public SingleResultRes updateMobileNo(UpdateMobileNoReq req) throws BaseRestException;

    @Deprecated
    public SingleResultRes unbindOpenID(UnBindOpenIDReq req) throws BaseRestException;

    /**
     * 根据uid或extUid修改用户密码
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public SingleResultRes changePassword(ChangePasswordReq req) throws BaseRestException;

}
