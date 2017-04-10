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
import com.wepiao.admin.user.rest.msg.UserInfoGetByUIDReq;
import com.wepiao.admin.user.rest.msg.UserInfoGetRes;
import com.wepiao.admin.user.service.UserInfoQueryService;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.StringUtil;

/**
 * 
 * @author jinsong
 *
 */
@Api(value = "/getuserinfobymemberid")
@Path("/")
public class UserInfoGetByUIDController extends BaseController {

    private static final Logger  logger = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    @Autowired
    private UserInfoQueryService userInfoQueryService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getuserinfobymemberid")
    public Response getUserInfoByMemberId(UserInfoGetByUIDReq reqJava) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        UserInfoGetRes resObj = userInfoQueryService.getUserByUID(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getuserinfobyuid")
    public Response getUserInfoByUID(String req) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, req);
        UserInfoGetByUIDReq reqJava = parseObject(req, UserInfoGetByUIDReq.class);
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        UserInfoGetRes resObj = userInfoQueryService.getUserByUID(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(UserInfoGetByUIDReq req) {
        if (StringUtil.isEmptyCheckTrim(req.getMemberId())) {
            if (!StringUtil.isEmptyCheckTrim(req.getUID())) {
                req.setMemberId(req.getUID());
            }
            if (StringUtil.isEmptyCheckTrim(req.getMemberId())) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_MEMBER_ID);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_MEMBER_ID);
            }
        }
    }
}
