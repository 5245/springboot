package com.wepiao.admin.user.service;

import com.wepiao.admin.user.rest.msg.IdRelationGetReq;
import com.wepiao.admin.user.rest.msg.IdRelationGetRes;
import com.wepiao.admin.user.rest.msg.OpenIdInfoGetReq;
import com.wepiao.admin.user.rest.msg.OpenIdInfoGetRes;
import com.wepiao.admin.user.rest.msg.OpenIdListGetByMobileReq;
import com.wepiao.admin.user.rest.msg.OpenIdListGetByMobileRes;
import com.wepiao.admin.user.rest.msg.UserFirstRegistTimeRes;
import com.wepiao.admin.user.rest.msg.UserInfoGetByMobileNoReq;
import com.wepiao.admin.user.rest.msg.UserInfoGetByOpenIdReq;
import com.wepiao.admin.user.rest.msg.UserInfoGetByUIDReq;
import com.wepiao.admin.user.rest.msg.UserInfoGetRes;
import com.wepiao.admin.user.rest.msg.UserInfoUploadPhotoReq;
import com.wepiao.admin.user.rest.msg.UserInfoUploadPhotoRes;
import com.wepiao.admin.user.rest.msg.UserProfileGetByUIDReq;
import com.wepiao.admin.user.rest.msg.UserProfileGetRes;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface UserInfoQueryService {
    /**
     * 通过uid得到用户基本信息和扩展信息
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public UserProfileGetRes getUserProfileByUID(UserProfileGetByUIDReq req) throws BaseRestException;

    /**
     * 通过uid得到用户基本信息
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public UserInfoGetRes getUserByUID(UserInfoGetByUIDReq req) throws BaseRestException;

    /**
     * 通过openId得到用户基本信息
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public UserInfoGetRes getUserByOpenId(UserInfoGetByOpenIdReq req) throws BaseRestException;

    /**
     * 通过电话号码得到用户基本信息
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public UserInfoGetRes getUserByMobileNo(UserInfoGetByMobileNoReq req) throws BaseRestException;

    /**
     * 根据openId得到openInfo
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public OpenIdInfoGetRes getOpenIdInfo(OpenIdInfoGetReq req) throws BaseRestException;

    @Deprecated
    public UserInfoUploadPhotoRes uploadUserPhoto(UserInfoUploadPhotoReq req) throws BaseRestException;

    /**
     * 通过uid获取用户的关系
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public IdRelationGetRes getIdRelation(IdRelationGetReq req) throws BaseRestException;

    /**
     * 通过用户的手机号获取openID的列表
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public OpenIdListGetByMobileRes getOpenIdListByMobile(OpenIdListGetByMobileReq req) throws BaseRestException;

    /**
     * 通过openid获取用户最早的注册时间
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public UserFirstRegistTimeRes getUserFirstRegistTime(OpenIdInfoGetReq req) throws BaseRestException;

}
