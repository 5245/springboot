package com.wepiao.admin.user.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wepiao.admin.user.rest.msg.BlackListAddReq;
import com.wepiao.admin.user.rest.msg.BlackListRemoveReq;
import com.wepiao.admin.user.rest.msg.BlackListRetrieveRes;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.service.BlackListService;
import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.OpenId;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;

@Service
public class BlackListServiceImpl implements BlackListService {

    private static final Logger logger = LoggerFactory.getLogger(BlackListServiceImpl.class);

    @Autowired
    private OpenIdInfoHandler   openIdInfoHandler;

    @Override
    public BlackListRetrieveRes getBlackList(String reqId) throws BaseRestException {
        BlackListRetrieveRes res = null;
        try {
            List<OpenId> blackList = openIdInfoHandler.getBlackList();
            res = new BlackListRetrieveRes(blackList);
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, reqId, me.getMessage());
            throw new BaseRestException(reqId, ResponseInfoEnum.E50001);
        }
        return res;
    }

    @Override
    public SingleResultRes addUserToBlackList(BlackListAddReq req) throws BaseRestException {
        SingleResultRes res = null;
        int result = 0;
        try {
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(req.getOpenID());
            if (openIdInfo == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, req.getOpenID()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                // 用户已经在黑名单上, 抛出异常
                if (Status.BLACK == openIdInfo.getStatus()) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.DUPLICATED_BLACK_ADD_DETAIL, req.getOpenID()));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10008, LogMsg.DUPLICATED_BLACK_ADD);
                } else {
                    // 更新用户状态到黑名单状态(status=8)
                    int updateResult = openIdInfoHandler.updateStatus(req.getOpenID(), OtherID.parseInt(req.getOtherID()), Status.BLACK);
                    if (updateResult != 1) {
                        result = 1;
                    }
                }
            }
            res = new SingleResultRes(result);
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    @Override
    public SingleResultRes removeUserFromBlackList(BlackListRemoveReq req) throws BaseRestException {
        SingleResultRes res = null;
        int result = 0;
        try {
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(req.getOpenID());
            if (openIdInfo == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, req.getOpenID()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                // 用户不在黑名单上
                if (Status.BLACK != openIdInfo.getStatus()) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.ERR_BLACK_REMOVE_DETAIL, req.getOpenID()));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10001, LogMsg.ERR_BLACK_REMOVE);
                } else {
                    // 从黑名单中移除，则用户状态变为普通用户(vaule=1)
                    int updateResult = openIdInfoHandler.updateStatus(req.getOpenID(), OtherID.parseInt(req.getOtherID()), Status.NORMAL);
                    if (updateResult != 1) {
                        result = 1;
                    }
                }
            }
            res = new SingleResultRes(result);
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }
}
