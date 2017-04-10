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
import com.wepiao.admin.user.rest.msg.dragon.UnionIdGetByOpenIdReq;
import com.wepiao.admin.user.rest.msg.dragon.UnionIdGetByOpenIdRes;
import com.wepiao.admin.user.service.dragon.UserInfoQueryService4Dragon;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;

/**
 * 
 * @author Jin Song
 *
 */
@Api(value = "/getuniqueidbyopenid4dragon", produces = "application/json")
@Path("/getuniqueidbyopenid4dragon")
public class UniqueIdGetByOpenIdController extends BaseController {

    private static final Logger         logger = LoggerFactory.getLogger(UniqueIdGetByOpenIdController.class);

    @Autowired
    private UserInfoQueryService4Dragon userInfoQueryService;

    /**
     * 通过openid查询用户关联的上一级unionid，若没有返回自身
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUniqueIdByOpenId(UnionIdGetByOpenIdReq reqJava) {
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        UnionIdGetByOpenIdRes resObj = userInfoQueryService.getUniqueIdByOpenId(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(UnionIdGetByOpenIdReq req) {
        if (req.getOpenId() == null || req.getOpenId().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OPEN_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OPEN_ID);
        }
    }
}
