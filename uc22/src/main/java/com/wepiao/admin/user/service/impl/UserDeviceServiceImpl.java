package com.wepiao.admin.user.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.rest.msg.UserDeviceAddReq;
import com.wepiao.admin.user.rest.msg.UserDeviceChangeCheckReq;
import com.wepiao.admin.user.rest.msg.UserDeviceChangeCheckRes;
import com.wepiao.admin.user.service.UserDeviceService;
import com.wepiao.admin.user.service.handler.UserDeviceHandler;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.handler.MobileNum2UIDHandler;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    private static final Logger  logger = LoggerFactory.getLogger(UserDeviceServiceImpl.class);

    @Autowired
    private MobileNum2UIDHandler mobileNum2UIDHandler;

    @Autowired
    private UserDeviceHandler    userDeviceHandler;

    @Override
    public UserDeviceChangeCheckRes isDeviceChanged(UserDeviceChangeCheckReq req, DeviceInfo deviceinfo) throws BaseRestException {
        boolean checkResult = false;
        try {
            String mobileNo = req.getMobileNo();
            Integer memberId = mobileNum2UIDHandler.getUIDByMobileNo(mobileNo);
            if (Constants.NOT_EXISTED_UID == memberId) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MOBILE2UID_NOT_FOUND, req.getMobileNo()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20008, req.getMobileNo());
            } else {
                DeviceInfo latestUsedDeviceInfo = userDeviceHandler.getLatestUsedUserDevice(String.valueOf(memberId), OtherID.UID);
                // 用户从未记录过设备信息
                if (null == latestUsedDeviceInfo) {
                    checkResult = false;
                } else {
                    if (deviceinfo.getDeviceId().equals(latestUsedDeviceInfo.getDeviceId())
                            && deviceinfo.getDeviceIdType() == latestUsedDeviceInfo.getDeviceIdType()) {
                        checkResult = false;
                    } else {
                        checkResult = true;
                    }
                }
            }
        } catch (JedisConnectionException je) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), je.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return new UserDeviceChangeCheckRes(checkResult);
    }

    @Override
    public SingleResultRes addDevice(UserDeviceAddReq req, List<DeviceInfo> deviceInfoList) throws BaseRestException {
        int result = 0;
        String mobileNo = req.getMobileNo();
        Integer memberId = mobileNum2UIDHandler.getUIDByMobileNo(mobileNo);
        if (Constants.NOT_EXISTED_UID == memberId) {
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MOBILE2UID_NOT_FOUND, req.getMobileNo()));
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20008, req.getMobileNo());
        } else {
            userDeviceHandler.addUserDevice(String.valueOf(memberId), OtherID.UID, deviceInfoList);
        }
        return new SingleResultRes(result);
    }
}