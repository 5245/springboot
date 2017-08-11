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
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.dao.UserDeliveryAddrMapper;
import com.wepiao.user.common.entry.UserDeliveryAddr;

@Repository
public class UserDeliveryAddrMapperImpl implements UserDeliveryAddrMapper {

    private Logger                          logger         = LoggerFactory.getLogger(getClass());
    private String                          namespace      = "com.wepiao.user.common.dao.UserDeliveryAddrMapper";

    //0表示非默认地址,1表示是默认收货地址
    private static final int                IS_NOT_DEFAULT = 0;

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(UserDeliveryAddr addr) {
        String openId = addr.getOpenId();
        addr.setTableIndex(daoSupport.getTableIndex(openId));
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.insert(namespace + ".insert", addr);
        logger.info(LogMsg.INSERT_DB_FOR_USER_DELIVERY_ADDR, JSON.toJSONString(addr));
        return affectRows;
    }

    @Override
    public int update(UserDeliveryAddr addr) {
        String openId = addr.getOpenId();
        addr.setTableIndex(daoSupport.getTableIndex(openId));
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.update(namespace + ".update", addr);
        logger.info(LogMsg.UPDATE_DB_FOR_USER_DELIVERY_ADDR, JSON.toJSONString(addr));
        return affectRows;
    }

    @Override
    @Deprecated
    public int updateCancelDefaultAddrByOpenId(String openId, Integer id) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> uidMap = new HashMap<String, Object>();
        uidMap.put("tableIndex", tableIndex);
        uidMap.put("openId", openId);
        uidMap.put("isDefault", IS_NOT_DEFAULT);
        SqlSession session = daoSupport.getSqlSession(openId);
        return session.update(namespace + ".updateCancelDefaultAddrByOpenId", uidMap);
    }

    @Override
    public UserDeliveryAddr queryOneById(String openId, Integer id) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> uidMap = new HashMap<String, Object>();
        uidMap.put("tableIndex", tableIndex);
        uidMap.put("id", id);
        uidMap.put("openId", openId);
        uidMap.put("isDel", Constants.IS_NOT_DEL);
        SqlSession session = daoSupport.getSqlSession(openId);
        return session.selectOne(namespace + ".queryOneById", uidMap);
    }

    @Override
    public List<UserDeliveryAddr> queryByOpenId(String openId) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> uidMap = new HashMap<String, Object>();
        uidMap.put("tableIndex", tableIndex);
        uidMap.put("openId", openId);
        uidMap.put("isDel", Constants.IS_NOT_DEL);
        uidMap.put("max", Constants.MAX_ADDRESS_COUNT);
        SqlSession session = daoSupport.getSqlSession(openId);
        List<UserDeliveryAddr> addrs = session.selectList(namespace + ".queryByOpenId", uidMap);
        return addrs;
    }

}
