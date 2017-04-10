package com.wepiao.admin.user.service.dragon;

import com.wepiao.admin.user.rest.msg.dragon.OpenIdGetByUIDReq;
import com.wepiao.admin.user.rest.msg.dragon.OpenIdGetByUIDRes;
import com.wepiao.admin.user.rest.msg.dragon.OpenIdInfoGetReq;
import com.wepiao.admin.user.rest.msg.dragon.OpenIdInfoGetRes;
import com.wepiao.admin.user.rest.msg.dragon.UnionIdGetByOpenIdReq;
import com.wepiao.admin.user.rest.msg.dragon.UnionIdGetByOpenIdRes;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface UserInfoQueryService4Dragon {
    /**
     * 通过用户的uid获取openID
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public OpenIdGetByUIDRes getOpenIdByUID(OpenIdGetByUIDReq req) throws BaseRestException;

    /**
     * 通过用户的openid或者uninonId获取openId的昵称头像
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public OpenIdInfoGetRes getOpenIdInfo(OpenIdInfoGetReq req) throws BaseRestException;

    /**
     * 通过用户的openid获取unionid,如果没有返回其本身
     * @param req
     * @return
     * @throws BaseRestException
     */
    public UnionIdGetByOpenIdRes getUniqueIdByOpenId(UnionIdGetByOpenIdReq req) throws BaseRestException;
}
