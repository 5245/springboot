package com.wepiao.admin.user.rest.controller;

import io.swagger.annotations.Api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.admin.user.rest.msg.BlackListRetrieveRes;
import com.wepiao.admin.user.service.BlackListService;
import com.wepiao.user.common.constant.LogMsg;

/**
 * 
 * @author jinsong
 *
 */
@Api(value = "/getblacklist", produces = "application/json")
@Path("/getblacklist")
public class BlackListGetController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BlackListGetController.class);

    @Autowired
    private BlackListService    blackListService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBlackList() {
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, reqId, null);
        BlackListRetrieveRes resObj = blackListService.getBlackList(reqId);
        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, reqId, res);
        return Response.ok().entity(res).build();
    }
}
