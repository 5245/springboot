package com.wepiao.admin.user.service;

import com.wepiao.admin.user.rest.msg.BlackListAddReq;
import com.wepiao.admin.user.rest.msg.BlackListRemoveReq;
import com.wepiao.admin.user.rest.msg.BlackListRetrieveRes;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface BlackListService {

    public BlackListRetrieveRes getBlackList(String reqId) throws BaseRestException;

    public SingleResultRes addUserToBlackList(BlackListAddReq req) throws BaseRestException;

    public SingleResultRes removeUserFromBlackList(BlackListRemoveReq req) throws BaseRestException;
}
