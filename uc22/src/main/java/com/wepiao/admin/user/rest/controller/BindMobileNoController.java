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
import com.wepiao.admin.user.rest.msg.BindMobileNoReq;
import com.wepiao.admin.user.rest.msg.BindMobileNoRes;
import com.wepiao.admin.user.service.UserInfoUpdateService;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.redis.RedisMapper;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.MobilePhoneUtils;
import com.wepiao.user.common.util.RestClient;
import com.wepiao.user.common.util.StringUtil;
import com.wepiao.user.common.util.TianYuRegisterProtectUtils;

/**
 * 
 * @author jinsong
 *
 */
@Api(value = "/bindmobileno", produces = "application/json")
@Path("/bindmobileno")
public class BindMobileNoController extends BaseController {

    private static final Logger   logger         = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    private static final Logger   securityLogger = LoggerFactory.getLogger(Constants.LOG_SECURITY);

    @Autowired
    private UserInfoUpdateService userInfoUpdateService;

    /**
     * 第三方用户openID来绑定手机号
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bindMobileNo(BindMobileNoReq reqJava) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);
        String formattedMobileNo = MobilePhoneUtils.judgeAndFormatMobileNo(reqJava.getMobileNo(), reqId);
        // 检查手机号是否在黑名单中
        JSONObject tianyuScanResult = checkMobileNoByTianYu(formattedMobileNo, reqId, hh);
        checkMobileNoByWeiYing(formattedMobileNo, reqId);

        reqJava.setMobileNo(formattedMobileNo);

        BindMobileNoRes resObj = userInfoUpdateService.bindMobileNo(reqJava);
        resObj.setTianyuScanResult(tianyuScanResult);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(BindMobileNoReq req) {
        if (null == req.getOpenId() || req.getOpenId().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OPEN_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OPEN_ID);
        }
        // otherId不能为0(未设置), otherId为微信的时候，unionId不能为null
        if (req.getOtherId() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OTHER_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OTHER_ID);
        } else {
            if (OtherID.WEIXIN.getIntVal() == req.getOtherId() && (null == req.getUnionId() || 0 == req.getUnionId().length())) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_UNION_ID);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_UNION_ID);
            }
        }

        if (req.getMobileNo() == null || req.getMobileNo().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_MOBILE_NO);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_MOBILE_NO);
        }
    }

    // 防黄牛作弊，从天御过滤手机号
    private JSONObject checkMobileNoByTianYu(String mobileNo, String reqId, HttpHeaders hh) {
        JSONObject jsonObj = null;
        try {
            String userIp = hh.getHeaderString(Constants.USER_IP);
            if (!StringUtil.isEmptyCheckTrim(userIp)) {
                String tianYuRegisterProtectUrl = TianYuRegisterProtectUtils.makeMobileNoURL(mobileNo, userIp);
                String response = RestClient.sendHttpGet(tianYuRegisterProtectUrl);
                jsonObj = parseObject(response);
                // level大于2的均为深度恶意用户
                if (null != jsonObj && (Integer) jsonObj.get("level") > 2) {
                    String msg = LogMessageFormatter.format(LogMsg.RUSH_MOBILE_SUSPENDED_BY_TIANYU, mobileNo);
                    securityLogger.warn(LogMsg.BASE_MSG, reqId, msg);
                }
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (Exception e) {
            // nothing to do.仅仅抛出自封装的exception
        }
        return jsonObj;
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
