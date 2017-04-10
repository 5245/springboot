package com.wepiao.admin.user.rest.controller.dragon;

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

import com.wepiao.admin.user.rest.controller.BaseController;
import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.admin.user.rest.msg.dragon.OpenIdGetByUIDReq;
import com.wepiao.admin.user.rest.msg.dragon.OpenIdGetByUIDRes;
import com.wepiao.admin.user.service.dragon.UserInfoQueryService4Dragon;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.StringUtil;

/**
 * 
 * @author Jin Song
 *
 */
@Api(value = "getopenidbymemberid4dragon")
@Path("/")
public class OpenIdGetByUIDController extends BaseController {

    private static final Logger         logger = LoggerFactory.getLogger(OpenIdGetByUIDController.class);

    @Autowired
    private UserInfoQueryService4Dragon userInfoQueryService;

    /**
     * 通过memberid查询用户关联的openid(包括unionid)
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getopenidbymemberid4dragon")
    public Response getOpenIdByMemberId(OpenIdGetByUIDReq reqJava) {
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        OpenIdGetByUIDRes resObj = userInfoQueryService.getOpenIdByUID(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, reqId, res);
        return Response.ok().entity(res).build();
    }

    /**
     * 通过uid查询用户关联的openid(包括unionid)
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getopenidbyuid4dragon")
    public Response getOpenIdByUID(String req) {
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, reqId, req);
        OpenIdGetByUIDReq reqJava = parseObject(req, OpenIdGetByUIDReq.class);
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        OpenIdGetByUIDRes resObj = userInfoQueryService.getOpenIdByUID(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(OpenIdGetByUIDReq req) {
        if (req.getMemberId() == null || req.getMemberId().length() == 0) {
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
