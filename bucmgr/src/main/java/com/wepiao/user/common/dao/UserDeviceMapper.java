package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.UserDevice;
import com.wepiao.user.common.entry.enumeration.OtherID;

public interface UserDeviceMapper {

    public int insertUserDevice(UserDevice userDevice);

    public List<UserDevice> queryUserDevice(String openId);

    public UserDevice getLatestUsedUserDevice(String id, OtherID idType);
}
