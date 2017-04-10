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
import com.wepiao.admin.user.rest.msg.OpenIdInfoGetReq;
import com.wepiao.admin.user.rest.msg.OpenIdInfoGetRes;
import com.wepiao.admin.user.service.UserInfoQueryService;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;

/**
 * 
 * @author jinsong
 *
 */
@Api(value = "/getopenid", produces = "application/json")
@Path("/getopenid")
public class OpenIdGetController extends BaseController {

    private static final Logger  logger = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    @Autowired
    private UserInfoQueryService userInfoQueryService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOpenIdInfo(OpenIdInfoGetReq reqJava) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        OpenIdInfoGetRes resObj = userInfoQueryService.getOpenIdInfo(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(OpenIdInfoGetReq req) {
        if (req.getOpenID() == null || req.getOpenID().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OPEN_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OPEN_ID);
        }
    }
}
