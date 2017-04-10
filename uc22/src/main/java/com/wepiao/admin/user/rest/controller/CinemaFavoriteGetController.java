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
import com.wepiao.admin.user.rest.msg.CinemaFavoriteListReq;
import com.wepiao.admin.user.rest.msg.CinemaFavoriteListRes;
import com.wepiao.admin.user.service.CinemaFavoriteService;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;

/**
 * 
 * @author jinsong
 *
 */
@Api(value = "/listcinemafromfavorites", produces = "application/json")
@Path("/listcinemafromfavorites")
public class CinemaFavoriteGetController extends BaseController {

    private static final Logger   logger = LoggerFactory.getLogger(CinemaFavoriteGetController.class);

    @Autowired
    private CinemaFavoriteService cinemaFavoriteService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCinemaFavorite(CinemaFavoriteListReq reqJava) {
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        CinemaFavoriteListRes resObj = cinemaFavoriteService.getCinemaFavoritesByOpenId(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(CinemaFavoriteListReq req) {
        if (req.getOpenID() == null || req.getOpenID().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OPEN_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OPEN_ID);
        }
        // otherId不能为0(未设置)
        if (req.getOtherID() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OTHER_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OTHER_ID);
        }
    }
}
