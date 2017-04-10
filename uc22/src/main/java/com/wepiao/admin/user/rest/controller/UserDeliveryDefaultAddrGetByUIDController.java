package com.wepiao.admin.user.rest.controller;

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
import com.wepiao.admin.user.rest.msg.UserDeliveryAddrCRUDReq;
import com.wepiao.admin.user.rest.msg.UserDeliveryAddrCRURes;
import com.wepiao.admin.user.service.UserDeliveryAddrService;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.StringUtil;

/**
 * 
 * @author jinsong
 *
 */
@Path("/getdefaultpdabymemberid")
public class UserDeliveryDefaultAddrGetByUIDController extends BaseController {

    private static final Logger     logger = LoggerFactory.getLogger(UserDeliveryDefaultAddrGetByUIDController.class);

    @Autowired
    private UserDeliveryAddrService userDeliveryAddrService;

    /**
     * 获取某一用户的默认收货地址
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDefaultPDAByUID(UserDeliveryAddrCRUDReq reqJava) {
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);
        //查询用户默认收货地址
        UserDeliveryAddrCRURes defaultAddr = userDeliveryAddrService.queryDefaultUserDeliverAddr(reqJava);
        String res = BaseResWrapper.wrapToJSONString(reqId, defaultAddr);
        logger.debug(LogMsg.WS_RES, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(UserDeliveryAddrCRUDReq req) {
        if (StringUtil.isEmptyCheckTrim(req.getMemberId())) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_MEMBER_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_MEMBER_ID);
        }
    }
}
