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

import com.alibaba.fastjson.JSONObject;
import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.admin.user.rest.msg.MobileVerifyReq;
import com.wepiao.admin.user.rest.msg.MobileVerifyRes;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.RestClient;
import com.wepiao.user.common.util.TianYuAntiRushUtils;
import com.wepiao.user.common.util.TianYuLoginProtectUtils;
import com.wepiao.user.common.util.TianYuRegisterProtectUtils;

/**
 * 
 * @author jin Song
 * 该调用不需要Request-Id
 *
 */
@Api(value = "/mobileverify", produces = "application/json")
@Path("/mobileverify")
public class MobileVerifyController extends BaseController {
    private static final Logger logger         = LoggerFactory.getLogger(MobileVerifyController.class);

    private static final Logger securityLogger = LoggerFactory.getLogger(Constants.LOG_SECURITY);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verify(MobileVerifyReq reqJava) {
        logger.debug(LogMsg.WS_REQ_NO_REQ_ID, reqJava.toString());
        String reqId = checkAndRetrieveReqId();
        checkParam(reqJava);
        MobileVerifyRes resObj = checkMobileNoByTianYu(reqJava.getMobileNo(), reqJava.getUserIp(), reqJava.getOperation(), reqJava.getTimeStamp(),
                reqId);
        String res = BaseResWrapper.wrapToJSONString(resObj);
        logger.debug(LogMsg.WS_RES_NO_REQ_ID, res);
        return Response.ok().entity(res).build();
    }

    private MobileVerifyRes checkMobileNoByTianYu(String mobileNo, String userIp, String operation, long timeStamp, String reqId)
            throws BaseRestException {
        MobileVerifyRes res = new MobileVerifyRes(true);
        try {
            String tianYuUrl = null;
            if (null != operation && 0 < operation.length()) {
                if (operation.equalsIgnoreCase("register")) {
                    tianYuUrl = TianYuRegisterProtectUtils.makeMobileNoURL(mobileNo, userIp, timeStamp);
                } else if (operation.equalsIgnoreCase("login")) {
                    tianYuUrl = TianYuLoginProtectUtils.makeMobileNoURL(mobileNo, userIp, timeStamp);
                } else if (operation.equalsIgnoreCase("antirush")) {
                    tianYuUrl = TianYuAntiRushUtils.makeMobileNoURL(mobileNo, userIp, timeStamp);
                }
            }
            tianYuUrl = TianYuAntiRushUtils.makeMobileNoURL(mobileNo, userIp, timeStamp);

            String response = RestClient.sendHttpGet(tianYuUrl);
            JSONObject jsonObj = parseObject(response);
            // level大于2的均为深度恶意用户
            Integer level = (Integer) jsonObj.get("level");
            res.setLevel(level);
            if (null != jsonObj && level > 2) {
                String msg = LogMessageFormatter.format(LogMsg.RUSH_MOBILE_SUSPENDED_BY_TIANYU, mobileNo);
                securityLogger.warn(LogMsg.BASE_MSG, reqId, msg);
                res.setIsSafe(false);
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (Exception e) {
            // nothing to do.仅仅抛出自封装的exception
        }
        return res;
    }

    private void checkParam(MobileVerifyReq req) {
        if (req.getMobileNo() == null || req.getMobileNo().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_MOBILE_NO);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_MOBILE_NO);
        }
        if (req.getUserIp() == null || req.getUserIp().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_USER_IP);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_USER_IP);
        }
    }
}
