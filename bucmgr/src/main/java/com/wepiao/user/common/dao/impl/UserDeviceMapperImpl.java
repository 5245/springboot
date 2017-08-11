package com.wepiao.user.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.wepiao.user.common.base.dao.SqlSessionMutilSourceDaoSupport;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.dao.UserDeviceMapper;
import com.wepiao.user.common.entry.UserDevice;
import com.wepiao.user.common.entry.enumeration.OtherID;

@Repository
public class UserDeviceMapperImpl implements UserDeviceMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.UserDeviceMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insertUserDevice(UserDevice userDevice) {
        String id = userDevice.getId();
        userDevice.setTableIndex(daoSupport.getTableIndex(id));
        SqlSession session = daoSupport.getSqlSession(id);
        int affectRows = session.insert(namespace + ".insertUserDevice", userDevice);
        logger.info(LogMsg.INSERT_DB_FOR_USER_DEVICE, JSON.toJSONString(userDevice));
        return affectRows;
    }

    @Override
    public List<UserDevice> queryUserDevice(String openId) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> uidMap = new HashMap<String, Object>();
        uidMap.put("tableIndex", tableIndex);
        uidMap.put("id", openId);
        SqlSession session = daoSupport.getSqlSession(openId);
        List<UserDevice> devices = session.selectList(namespace + ".queryUserDevice", uidMap);
        return devices;
    }

    @Override
    public UserDevice getLatestUsedUserDevice(String id, OtherID idType) {
        UserDevice result = null;
        int tableIndex = daoSupport.getTableIndex(id);
        Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put("id", id);
        idMap.put("idType", idType);
        idMap.put("tableIndex", tableIndex);
        SqlSession session = daoSupport.getSqlSession(id);
        List<UserDevice> deviceList = session.selectList(namespace + ".getLatestUsedUserDevice", idMap);
        if (null != deviceList && !deviceList.isEmpty()) {
            result = deviceList.get(0);
        }
        return result;
    }
}
