package com.wepiao.admin.user.rest.controller;

import io.swagger.annotations.Api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
import com.wepiao.admin.user.rest.msg.UserInfoUpdateRes;
import com.wepiao.admin.user.service.UserInfoUpdateService;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.StringUtil;

/**
 * 根据用户uid修改用户基本信息
 * @author jinsong
 *
 */
@Api(value = "/updateuserinfo")
@Path("/updateuserinfo")
public class UserInfoUpdateController extends BaseController {

    private static final Logger   logger = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    @Autowired
    private UserInfoUpdateService userInfoUpdateService;

    /**
     * 根据用户uid修改用户基本信息
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserInfo(String req) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, req);
        // 针对update操作特殊处理，因为json body有可能仅仅含有部分字段的更新，
        // 传统的tojavaObject()无法判断字段不更新和字段为null的区别
        // 将所有存在的key统一变为小写，以便Service层的后续处理
        Map<String, Object> formattedReqMap = formatRequest(parseObject(req));
        checkParam(reqId, formattedReqMap);

        UserInfoUpdateRes resObj = userInfoUpdateService.updateUserInfo(reqId, formattedReqMap);

        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    private Map<String, Object> formatRequest(JSONObject req) {
        Map<String, Object> result = new HashMap<String, Object>();
        Set<String> keySet = req.keySet();
        for (String key : keySet) {
            Object val = req.get(key);
            result.put(key.toLowerCase(), val);
        }
        return result;
    }

    private void checkParam(String reqId, Map<String, Object> req) {
        Object memberIdObj = req.get("memberid");
        String memberId = handlerObjParam(reqId, memberIdObj);
        if (StringUtil.isEmptyCheckTrim(memberId)) {
            //判断uid的是否为空
            Object uidObj = req.get("uid");
            String uid = handlerObjParam(reqId, uidObj);
            //若uid也为空则抛出异常
            if (!StringUtil.isEmptyCheckTrim(uid)) {
                req.put("memberid", uid);
            } else {
                logger.warn(LogMsg.BASE_MSG, reqId, LogMsg.NO_MEMBER_ID);
                throw new BaseRestException(reqId, ResponseInfoEnum.E10002, LogMsg.NO_MEMBER_ID);
            }
        }
    }

    /**
     * handlerObjParam: <br/>
     * 处理memeberId和uid传整型和字符都兼容<br/>
     * @author liujie
     * @param obj
     * @return
     */
    private String handlerObjParam(String reqId, Object obj) {
        String objStr = null;
        if (null != obj) {
            if (obj instanceof String) {
                objStr = (String) obj;
            } else if (obj instanceof Integer) {
                Integer objInt = (Integer) obj;
                objStr = objInt.toString();
            } else {
                logger.warn(LogMsg.BASE_MSG, reqId, LogMsg.NO_MEMBER_ID);
                throw new BaseRestException(reqId, ResponseInfoEnum.E10002, LogMsg.NO_MEMBER_ID);
            }
        }

        return objStr;
    }
}
