package com.wepiao.admin.user.rest.controller;

import io.swagger.annotations.Api;

import java.util.List;

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
import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.UserDeviceChangeCheckReq;
import com.wepiao.admin.user.rest.msg.UserDeviceChangeCheckRes;
import com.wepiao.admin.user.service.UserDeviceService;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.enumeration.DeviceIdType;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.MobilePhoneUtils;

/**
 * 
 * @author Jin Song
 *
 */
@Api(value = "/isdevicechanged")
@Path("/isdevicechanged")
public class UserDeviceChangeCheckController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(Constants.LOG_USER_INFO);

    @Autowired
    private UserDeviceService   userDeviceService;

    /**
     * 通过手机号注册新用户
     * @param hh
     * @param req
     * @return
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response isDeviceChanged(UserDeviceChangeCheckReq reqJava) {
        String uri = getRequestURI();
        String reqId = checkAndRetrieveReqId();
        logger.debug(LogMsg.WS_REQ, uri, reqId, reqJava.toString());
        checkFastJsonParseResult(reqJava, reqId);
        List<DeviceInfo> deviceInfoList = retrieveDeviceId(hh);
        checkParam(reqJava, deviceInfoList);
        DeviceInfo deviceInfo = deviceInfoList.get(0);

        String formattedMobileNo = MobilePhoneUtils.judgeAndFormatMobileNo(reqJava.getMobileNo(), reqId);
        reqJava.setMobileNo(formattedMobileNo);

        UserDeviceChangeCheckRes resObj = userDeviceService.isDeviceChanged(reqJava, deviceInfo);
        String res = BaseResWrapper.wrapToJSONString(reqId, resObj);
        logger.debug(LogMsg.WS_RES, uri, reqId, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(UserDeviceChangeCheckReq req, List<DeviceInfo> deviceInfoList) {
        if (req.getMobileNo() == null || req.getMobileNo().length() == 0) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.NO_MOBILE_NO);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.NO_MOBILE_NO);
        }
        if (deviceInfoList == null || deviceInfoList.isEmpty() || deviceInfoList.size() > 1) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.ERR_DEVICE_ID_AMOUNT);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.ERR_DEVICE_ID_AMOUNT);
        } else {
            // 仅仅接受 IDFA和DeviceId
            DeviceIdType deviceIdType = deviceInfoList.get(0).getDeviceIdType();
            if (DeviceIdType.DEVICE_ID != deviceIdType && DeviceIdType.IDFA != deviceIdType) {
                String errorMsg = LogMessageFormatter.format(LogMsg.ERR_DEVICE_ID_TYPE, deviceIdType.toString());
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), errorMsg);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, errorMsg);
            }
        }
    }
}
