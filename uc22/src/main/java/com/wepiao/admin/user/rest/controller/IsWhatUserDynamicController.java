package com.wepiao.admin.user.rest.controller;

import io.swagger.annotations.Api;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.admin.user.rest.msg.UserTagGetReq;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.HttpAsyncUtil;

/**
 * 
 * @author jinsong
 *
 */
@Api(value = "/getdynamictag")
@Path("/getdynamictag")
public class IsWhatUserDynamicController extends BaseController {

    private static final Logger logger              = LoggerFactory.getLogger(Constants.LOG_USER_TAG);

    //ucopen动态标签查询访问地址
    private static final String GET_DYNAMIC_TAG_URL = Constants.UCOPEN_URL + "getdynamictag";

    //uc-ucopen请求的requestId前缀
    private static final String REQUESTID_PREFIX    = "uc2ucopen_";

    /**
     * 判断用户是否有某动态标签。
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response isWhatUserDynamic(String req) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, req);
        UserTagGetReq reqJava = parseObject(req, UserTagGetReq.class);
        checkFastJsonParseResult(reqJava, reqId);
        checkParam(reqJava);

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.SHORT_REQ_ID, REQUESTID_PREFIX + reqId);
        String response = HttpAsyncUtil.post(GET_DYNAMIC_TAG_URL, req, headers);
        logger.debug(LogMsg.WS_RES, uri, reqId, response);

        return Response.ok().entity(response).build();
    }

    private void checkParam(UserTagGetReq req) {
        if (req.getId() == null || req.getId().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_ID);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_ID);
        }
        // otherId不能小于0(非负)
        if (req.getIdType() < 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_ID_TYPE);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_ID_TYPE);
        }
        if (req.getTagList() == null || req.getTagList().size() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_TAG_LIST);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_TAG_LIST);
        }
    }
}
