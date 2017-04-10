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

import com.alibaba.fastjson.JSONObject;
import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.admin.user.rest.msg.OpenIdRegisterReq;
import com.wepiao.admin.user.rest.msg.OpenIdRegisterRes;
import com.wepiao.admin.user.service.UserRegisterService;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.ConfigPropertiesUtil;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.RestClient;

/**
 * 
 * @author jinsong
 *
 */
@Api(value = "2-openidregister")
@Path("/openidregister")
public class OpenIDRegisterController extends BaseController {

    private static final Logger               logger     = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    private static final ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();
    /**
     * 通过第三方（微博、微信、手Q）用户的openId注册用户
     * @param hh
     * @param req
     */
    @Autowired
    private UserRegisterService               userRegisterService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(OpenIdRegisterReq reqJava) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);
        validateOpenId(reqJava, hh.getHeaderString(Constants.ACCESS_TOKEN), hh.getHeaderString(Constants.OAUTH_CONSUMER_KEY));

        OpenIdRegisterRes resObj = userRegisterService.registerThirdPartyUser(reqJava, retrieveDeviceId(hh));

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(OpenIdRegisterReq req) {
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

    /**
     * 验证openId的合法性
     * @param req
     * @param accessToken
     */
    private void validateOpenId(OpenIdRegisterReq req, String accessToken, String oauthConsumerKey) {
        boolean validated = true;
        boolean needValidate = Boolean.parseBoolean(configUtil.get("user.oauth2.verification.permitted"));
        if (needValidate) {
            if (accessToken == null || accessToken.length() == 0) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_ACCESS_TOKEN);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_ACCESS_TOKEN);
            }
            OtherID otherId = OtherID.parseInt(req.getOtherID());
            if (OtherID.WEIBO == otherId) {
                String weiboUrl = configUtil.get("user.oauth2.verification.url.weibo");
                String response = RestClient.sendHttpPost(String.format(weiboUrl, accessToken), null);
                JSONObject jsonObj = parseObject(response);
                if (null == jsonObj || jsonObj.containsKey("error")) {
                    validated = false;
                }
            } else if (OtherID.WEIXIN == otherId) {
                String wechatUrl = configUtil.get("user.oauth2.verification.url.wechat");
                String response = RestClient.sendHttpGet(String.format(wechatUrl, accessToken, req.getOpenID()));
                JSONObject jsonObj = parseObject(response);
                if (null == jsonObj || jsonObj.containsKey("errcode")) {
                    validated = false;
                }
            } else if (OtherID.QQ == otherId) {
                if (oauthConsumerKey == null || oauthConsumerKey.length() == 0) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_OAUTH_CONSUMER_KEY);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_OAUTH_CONSUMER_KEY);
                }
                String qqUrl = configUtil.get("user.oauth2.verification.url.qq");
                String response = RestClient.sendHttpGet(String.format(qqUrl, accessToken, oauthConsumerKey, req.getOpenID()));
                JSONObject jsonObj = parseObject(response);
                if (null == jsonObj || (Integer) jsonObj.get("ret") != 0) {
                    validated = false;
                }
            } else {
                validated = false;
            }
        }

        if (!validated) {
            String warnMsg = LogMessageFormatter.format(LogMsg.OPEN_ID_ILLEGAL_DETAIL, req.getOpenID(), req.getOtherID());
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), warnMsg);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.OPEN_ID_ILLEGAL);
        }
    }
}
