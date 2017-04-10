package com.wepiao.admin.user.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.wepiao.admin.user.rest.msg.BaseReq;
import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.enumeration.DeviceIdType;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.StringUtil;

public class BaseController {

    private static final Logger  logger = LoggerFactory.getLogger(BaseController.class);

    @Context
    protected HttpHeaders        hh;
    @Context
    protected HttpServletRequest request;

    /**
     * 从header里获取requestId 
     * @return
     */
    protected String checkAndRetrieveReqId() {
        String reqIdStr = hh.getHeaderString(Constants.SHORT_REQ_ID);
        if (StringUtil.isEmptyCheckTrim(reqIdStr)) {
            reqIdStr = "";
        }
        return reqIdStr;
    }

    protected String getRequestURI() {
        return request.getRequestURI();
    }

    /**
     * 从header里获取requestId 
     * @return
     */
    protected String getHeaderString(String name) {
        String value = "";
        if (!StringUtil.isEmptyCheckTrim(name)) {
            value = hh.getHeaderString(name);
        }
        return value;
    }

    public static JSONObject parseObject(String text) {
        if (StringUtil.isEmptyCheckTrim(text)) {
            logger.warn(LogMsg.BASE_MSG_NO_REQ_ID, LogMsg.JSON_PARSE_FAILED);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.JSON_PARSE_FAILED);
        }
        JSONObject obj;
        try {
            obj = JSON.parseObject(text);
        } catch (JSONException e) {
            logger.warn(LogMsg.BASE_MSG_NO_REQ_ID, LogMessageFormatter.format(LogMsg.JSON_PARSE_FAILED_DETAIL, text));
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.JSON_PARSE_FAILED);
        }
        return obj;
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        if (StringUtil.isEmptyCheckTrim(text)) {
            logger.warn(LogMsg.BASE_MSG_NO_REQ_ID, LogMsg.JSON_PARSE_FAILED);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.JSON_PARSE_FAILED);
        }
        T t;
        try {
            t = JSON.parseObject(text, clazz, new Feature[0]);
        } catch (JSONException e) {
            logger.warn(LogMsg.BASE_MSG_NO_REQ_ID, LogMessageFormatter.format(LogMsg.JSON_PARSE_FAILED_DETAIL, text));
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.JSON_PARSE_FAILED);
        }
        return t;
    }

    protected void checkFastJsonParseResult(BaseReq req, String reqId) {
        if (req == null) {
            logger.warn(LogMsg.BASE_MSG, reqId, LogMsg.JSON_PARSE_FAILED);
            throw new BaseRestException(reqId, ResponseInfoEnum.E10002, LogMsg.JSON_PARSE_FAILED);
        } else {
            req.setRequestId(reqId);
        }
    }

    protected List<DeviceInfo> retrieveDeviceId(HttpHeaders hh) {
        List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
        String idfaInfo = hh.getHeaderString(Constants.USER_IDFA);
        String imeiInfo = hh.getHeaderString(Constants.USER_IMEI);
        String macInfo = hh.getHeaderString(Constants.USER_MAC);
        String deviceIdInfo = hh.getHeaderString(Constants.USER_DEVICE_ID);
        if (null != idfaInfo && 0 < idfaInfo.length()) {
            deviceInfoList.add(new DeviceInfo(idfaInfo, DeviceIdType.IDFA));
        }
        if (null != imeiInfo && 0 < imeiInfo.length()) {
            deviceInfoList.add(new DeviceInfo(imeiInfo, DeviceIdType.IMEI));
        }
        if (null != macInfo && 0 < macInfo.length()) {
            deviceInfoList.add(new DeviceInfo(macInfo, DeviceIdType.MAC));
        }
        if (null != deviceIdInfo && 0 < deviceIdInfo.length()) {
            deviceInfoList.add(new DeviceInfo(deviceIdInfo, DeviceIdType.DEVICE_ID));
        }
        return deviceInfoList;
    }
}
