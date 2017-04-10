package com.wepiao.admin.user.rest.controller;

import io.swagger.annotations.Api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.rest.msg.UpdateMobileNoReq;
import com.wepiao.admin.user.service.UserInfoUpdateService;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.redis.RedisMapper;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.MobilePhoneUtils;
import com.wepiao.user.common.util.RestClient;
import com.wepiao.user.common.util.StringUtil;
import com.wepiao.user.common.util.TianYuAntiRushUtils;

/**
 * 
 * @author jinsong
 *
 */
@Api(value = "/updatemobileno", produces = "application/json")
@Path("/updatemobileno")
public class MobileNoUpdateController extends BaseController {

    private static final Logger   logger         = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    private static final Logger   securityLogger = LoggerFactory.getLogger(Constants.LOG_SECURITY);

    @Autowired
    private UserInfoUpdateService userInfoUpdateService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMobileNo(UpdateMobileNoReq reqJava) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);
        String formattedNewMobileNo = MobilePhoneUtils.judgeAndFormatMobileNo(reqJava.getNewMobileNo(), reqId);
        String formattedOldMobileNo = MobilePhoneUtils.judgeAndFormatMobileNo(reqJava.getOldMobileNo(), reqId);
        reqJava.setNewMobileNo(formattedNewMobileNo);
        reqJava.setOldMobileNo(formattedOldMobileNo);

        checkMobileNoByTianYu(formattedNewMobileNo, reqId, hh);
        checkMobileNoByWeiYing(formattedNewMobileNo, reqId);

        SingleResultRes resObj = userInfoUpdateService.updateMobileNo(reqJava);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(UpdateMobileNoReq req) {
        if (req.getMemberId() == null || req.getMemberId().length() == 0) {
            if (!StringUtil.isEmptyCheckTrim(req.getUid())) {
                req.setMemberId(req.getUid());
            }
            if (StringUtil.isEmptyCheckTrim(req.getMemberId())) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_MEMBER_ID);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_MEMBER_ID);
            }
        }
        if (req.getNewMobileNo() == null || req.getNewMobileNo().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_NEW_MOBILE_NO);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_NEW_MOBILE_NO);
        }
        if (req.getOldMobileNo() == null || req.getOldMobileNo().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OLD_MOBILE_NO);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OLD_MOBILE_NO);
        }
        if (req.getOldMobileNo().equals(req.getNewMobileNo())) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.DUPLICATED_MOBILE_BINDING, req.getNewMobileNo()));
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20003, LogMsg.DUPLICATED_MOBILE_BINDING);
        }
    }

    // 防黄牛作弊，从天御过滤手机号
    private void checkMobileNoByTianYu(String mobileNo, String reqId, HttpHeaders hh) throws BaseRestException {
        try {
            String userIp = hh.getHeaderString(Constants.USER_IP);
            if (!StringUtil.isEmptyCheckTrim(userIp)) {
                String tianYuRegisterProtectUrl = TianYuAntiRushUtils.makeMobileNoURL(mobileNo, userIp);
                String response = RestClient.sendHttpGet(tianYuRegisterProtectUrl);
                JSONObject jsonObj = parseObject(response);
                // level大于2的均为深度恶意用户
                if (null != jsonObj && (Integer) jsonObj.get("level") > 2) {
                    String msg = LogMessageFormatter.format(LogMsg.RUSH_MOBILE_SUSPENDED_BY_TIANYU, mobileNo);
                    securityLogger.warn(LogMsg.BASE_MSG, reqId, msg);
                    throw new BaseRestException(reqId, ResponseInfoEnum.E20016, msg);
                }
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (Exception e) {
            // nothing to do.仅仅抛出自封装的exception
        }
    }

    // 防黄牛作弊，从微影自有黑名单过滤手机号
    protected void checkMobileNoByWeiYing(String mobileNo, String reqId) throws BaseRestException {
        try {
            if (RedisMapper.isFakeMobileNo(mobileNo)) {
                String msg = LogMessageFormatter.format(LogMsg.RUSH_MOBILE_SUSPENDED_BY_WEIYING, mobileNo);
                securityLogger.warn(LogMsg.BASE_MSG, reqId, msg);
                throw new BaseRestException(reqId, ResponseInfoEnum.E20016, msg);
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (Exception e) {
            // nothing to do.仅仅抛出自封装的exception
        }
    }

}
