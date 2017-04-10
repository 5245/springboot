package com.wepiao.admin.user.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.admin.user.rest.msg.IdRelationGetReq;
import com.wepiao.admin.user.rest.msg.IdRelationGetRes;
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
@Api(value = "getidrelation", produces = "application/json")
@Path("/getidrelation")
public class IdRelationGetController extends BaseController {

    private static final Logger  logger = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    @Autowired
    private UserInfoQueryService userInfoQueryService;

    @ApiOperation(value = "getidrelation")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIdRelation(IdRelationGetReq reqJava) {
        checkParam(reqJava);
        IdRelationGetRes resObj = userInfoQueryService.getIdRelation(reqJava);
        String res = BaseResWrapper.wrapToJSONString(resObj);
        logger.debug(LogMsg.WS_RES_NO_REQ_ID, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(IdRelationGetReq req) {
        if (req.getId() == null || req.getId().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_ID);
        }
    }
}
