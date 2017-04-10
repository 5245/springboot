package com.wepiao.admin.user.rest.controller;

import io.swagger.annotations.Api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.admin.user.rest.msg.ChangePasswordReq;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.service.UserInfoUpdateService;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.StringUtil;

/**
 * 用户修改用户密码
 * @author jinsong
 *
 */
@Api(value = "/updatepassword", produces = "application/json")
@Path("/updatepassword")
public class ChangePasswordController extends BaseController {

    private static final Logger   logger = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    @Autowired
    private UserInfoUpdateService userInfoUpdateService;

    /**
     * 根据uid或者extUid修改用户密码
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(ChangePasswordReq reqJava) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        SingleResultRes resObj = userInfoUpdateService.changePassword(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(ChangePasswordReq req) {
        // UID不能为0
        if (StringUtil.isEmptyCheckTrim(req.getMemberId())) {
            if (!StringUtil.isEmptyCheckTrim(req.getUID())) {
                req.setMemberId(req.getUID());
            }
            if (StringUtil.isEmptyCheckTrim(req.getMemberId())) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_MEMBER_ID);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_MEMBER_ID);
            }
        }
        if (null == req.getOpType()) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OPTYPE);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OPTYPE);
        } else {
            if (Constants.CHANGE_PW_OP_TYPE == req.getOpType()) {
                if (StringUtil.isEmptyCheckTrim(req.getOldPassword())) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OLD_PASSWORD);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OLD_PASSWORD);
                }
                if (StringUtil.isEmptyCheckTrim(req.getNewPassword())) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_NEW_PASSWORD);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_NEW_PASSWORD);
                }
            } else if (Constants.RESET_PW_OP_TYPE == req.getOpType()) {
                if (StringUtil.isEmptyCheckTrim(req.getNewPassword())) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_NEW_PASSWORD);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_NEW_PASSWORD);
                }
                if (req.getMobileNo() == null || req.getMobileNo().length() == 0) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_MOBILE_NO);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_MOBILE_NO);
                }
            } else if (Constants.INIT_PW_OP_TYPE == req.getOpType()) {
                // 初始化密码的时候并不验证用用户的手机号
                if (StringUtil.isEmptyCheckTrim(req.getNewPassword())) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_NEW_PASSWORD);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_NEW_PASSWORD);
                }
            } else {
                String msg = LogMessageFormatter.format(LogMsg.ERR_OPTYPE, req.getOpType());
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), msg);
                throw new BaseRestException(ResponseInfoEnum.E10002, msg);
            }
        }

    }
}
