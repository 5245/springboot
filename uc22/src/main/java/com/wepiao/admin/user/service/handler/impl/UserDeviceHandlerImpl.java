package com.wepiao.admin.user.service.handler.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.service.handler.UserDeviceHandler;
import com.wepiao.user.common.dao.UserDeviceMapper;
import com.wepiao.user.common.entry.UserDevice;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;

/**
 * 在用户（App用户或者第三方用户）注册/登录的时将用户的device信息记录下来(只存db)
 * @author Jin Song
 *
 */
@Component
public class UserDeviceHandlerImpl implements UserDeviceHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserDeviceHandlerImpl.class);

    @Autowired
    private UserDeviceMapper    userDeviceMapper;

    @Override
    public void addUserDevice(String id, OtherID idType, List<DeviceInfo> deviceInfo) throws BaseRestException {
        try {
            if (null != deviceInfo && 0 < deviceInfo.size()) {
                for (DeviceInfo deviceInfoEle : deviceInfo) {
                    UserDevice device = new UserDevice(null, id, idType, //
                            deviceInfoEle.getDeviceId(), deviceInfoEle.getDeviceIdType(), null, null);
                    userDeviceMapper.insertUserDevice(device);
                }
            }
        } catch (DataAccessException me) {
            logger.error(ResponseInfoEnum.E50001.getMsg(), me);
            throw new BaseRestException(ResponseInfoEnum.E50001);
        }
    }

    @Override
    public DeviceInfo getLatestUsedUserDevice(String id, OtherID idType) throws BaseRestException {
        try {
            DeviceInfo deviceInfo = null;
            UserDevice userDevice = userDeviceMapper.getLatestUsedUserDevice(id, idType);
            if (null != userDevice) {
                deviceInfo = new DeviceInfo(userDevice.getDeviceId(), userDevice.getDeviceIdType());
            }
            return deviceInfo;
        } catch (DataAccessException me) {
            logger.error(ResponseInfoEnum.E50001.getMsg(), me);
            throw new BaseRestException(ResponseInfoEnum.E50001);
        }
    }
}
