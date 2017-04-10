package com.wepiao.admin.user.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.admin.user.rest.msg.UserTagAddReq;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.HttpAsyncUtil;
import com.wepiao.user.common.util.LogMessageFormatter;

/**
 * 
 * @author jin Song
 * 该调用不需要Request-Id
 *
 */
@Path("/addusertag")
public class AddUserTagController extends BaseController {
    private static final Logger logger              = LoggerFactory.getLogger(Constants.LOG_USER_TAG);

    //uc-ucopen添加静态标签访问URL
    private static final String ADD_STATIC_TAG_URL  = Constants.UCOPEN_URL + "addusertag/static";

    //uc-ucopen添加动态标签访问URL
    private static final String ADD_DYNAMIC_TAG_URL = Constants.UCOPEN_URL + "addusertag/dynamic";

    //uc-ucopen放请求的requestId前缀
    private static final String REQUESTID_PREFIX    = "uc2ucopen_";

    /**
     * 为某用户添加静态标签。
     * @param req
     * @return
     *
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserTag(String req) {
        return addStaticUserTag(req);
    }

    /**
     * 为某用户添加静态标签。
     * @param req
     * @return
     *
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/static")
    public Response addStaticUserTag(String req) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, req);
        UserTagAddReq reqJava = parseObject(req, UserTagAddReq.class);
        checkAndReorganizeParamForStatic(reqJava);

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.SHORT_REQ_ID, REQUESTID_PREFIX + reqId);

        String response = HttpAsyncUtil.post(ADD_STATIC_TAG_URL, req, headers);

        logger.debug(LogMsg.WS_RES, uri, reqId, response);

        return Response.ok().entity(response).build();
    }

    /**
     * 为某用户添加动态标签。
     * @param req
     * @return
     *
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dynamic")
    public Response addDynamicUserTag(String req) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, req);

        UserTagAddReq reqJava = parseObject(req, UserTagAddReq.class);
        checkAndReorganizeParamForDynamic(reqJava);

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.SHORT_REQ_ID, REQUESTID_PREFIX + reqId);

        String response = HttpAsyncUtil.post(ADD_DYNAMIC_TAG_URL, req, headers);

        logger.debug(LogMsg.WS_RES, uri, reqId, response);

        return Response.ok().entity(response).build();
    }

    /**
     * 检查静态标签参数，并设定syncmode=0(覆盖模式), needPersist=true(入库)
     * @param req
     * @return
     */
    private void checkAndReorganizeParamForStatic(UserTagAddReq req) {
        if (null == req.getId() || req.getId().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_ID);
        }
        // otherId不能小于0(非负)
        if (req.getIdType() < 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_ID_TYPE);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_ID_TYPE);
        }
        if (null == req.getTag() || req.getTag().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_TAG_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_TAG_ID);
        }

    }

    /**
     * 检查动态标签参数，并设定syncmode=1(数值累加模式)
     * @param req
     * @return
     */
    private void checkAndReorganizeParamForDynamic(UserTagAddReq req) {
        if (null == req.getId() || req.getId().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_ID);
        }
        // otherId不能小于0(非负)
        if (req.getIdType() < 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_ID_TYPE);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_ID_TYPE);
        }
        if (null == req.getTag() || req.getTag().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_TAG_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_TAG_ID);
        }

        if (null == req.getTagVal() || req.getTagVal().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_TAG_VAL);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_TAG_VAL);
        }

        // 动态标签的tagVal必须为数字，否则报错
        if (!isNumeric(req.getTagVal())) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.ERR_TAG_VAL_NUMERIC_DETAIL, req.getTagVal()));
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.ERR_TAG_VAL_NUMERIC);
        }

    }

    private static boolean isNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false && cs.charAt(0) != '-') {
                return false;
            }
        }
        return true;
    }
}
