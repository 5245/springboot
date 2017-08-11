package com.wepiao.user.common.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.wepiao.user.common.base.dao.SqlSessionMutilSourceDaoSupport;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.dao.VipCardOrderInfoMapper;
import com.wepiao.user.common.entry.VipCardOrderInfo;
import com.wepiao.user.common.entry.enumeration.VipCardOrderStatus;

/**
 * 
 * @description  @description  会员卡订单信息表DAO接口（按照orderId为key分库表）
 * @author sxk
 * @date 2016年9月30日
 *
 */
@Repository
public class VipCardOrderInfoMapperImpl implements VipCardOrderInfoMapper {
    private static Logger                   logger    = LoggerFactory.getLogger(VipCardOrderInfoMapperImpl.class);
    private static String                   namespace = "com.wepiao.user.common.dao.VipCardOrderInfoMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(VipCardOrderInfo vipCardOrderInfo) {
        String orderId = vipCardOrderInfo.getOrderId();
        vipCardOrderInfo.setTableIndex(daoSupport.getTableIndex(orderId));
        SqlSession session = daoSupport.getSqlSession(orderId);
        int affectRows = session.insert(namespace + ".insert", vipCardOrderInfo);
        logger.info(LogMsg.INSERT_DB_FOR_VIPCARDORDERINFO, JSON.toJSONString(vipCardOrderInfo));
        return affectRows;
    }

    @Override
    public int delete(String orderId) {
        int tableIndex = daoSupport.getTableIndex(orderId);
        Map<String, Object> orderIdMap = new HashMap<String, Object>();
        orderIdMap.put("tableIndex", tableIndex);
        orderIdMap.put("orderId", orderId);
        SqlSession session = daoSupport.getSqlSession(orderId);
        int affectRows = session.update(namespace + ".delete", orderIdMap);
        logger.info(LogMsg.DELETE_DB_FOR_VIPCARDORDERINFO, JSON.toJSONString(orderIdMap));
        return affectRows;
    }

    @Override
    public int update(VipCardOrderInfo vipCardOrderInfo) {
        String orderId = vipCardOrderInfo.getOrderId();
        vipCardOrderInfo.setTableIndex(daoSupport.getTableIndex(orderId));
        SqlSession session = daoSupport.getSqlSession(orderId);
        int affectRows = session.update(namespace + ".update", vipCardOrderInfo);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDORDERINFO, JSON.toJSONString(vipCardOrderInfo));
        return affectRows;

    }

    @Override
    public VipCardOrderInfo queryOneByOrderId(String orderId) {
        int tableIndex = daoSupport.getTableIndex(orderId);
        Map<String, Object> orderIdMap = new HashMap<String, Object>();
        orderIdMap.put("tableIndex", tableIndex);
        orderIdMap.put("orderId", orderId);
        SqlSession session = daoSupport.getSqlSession(orderId);
        VipCardOrderInfo vcOrderInfo = (VipCardOrderInfo) session.selectOne(namespace + ".queryOneByOrderId", orderIdMap);
        return vcOrderInfo;
    }

    @Override
    public int updateStatus(String orderId, VipCardOrderStatus status) {
        int tableIndex = daoSupport.getTableIndex(orderId);
        Map<String, Object> orderIdMap = new HashMap<String, Object>();
        orderIdMap.put("tableIndex", tableIndex);
        orderIdMap.put("orderId", orderId);
        orderIdMap.put("status", status);
        SqlSession session = daoSupport.getSqlSession(orderId);
        int affectRows = session.update(namespace + ".updateStatus", orderIdMap);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDORDERINFO, JSON.toJSONString(orderIdMap));
        return affectRows;
    }

    @Override
    public int updateLockTime(String orderId, Date lockTime) {
        int tableIndex = daoSupport.getTableIndex(orderId);
        Map<String, Object> orderIdMap = new HashMap<String, Object>();
        orderIdMap.put("tableIndex", tableIndex);
        orderIdMap.put("orderId", orderId);
        orderIdMap.put("lockTime", lockTime);
        SqlSession session = daoSupport.getSqlSession(orderId);
        int affectRows = session.update(namespace + ".updateLockTime", orderIdMap);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDORDERINFO, JSON.toJSONString(orderIdMap));
        return affectRows;
    }

    @Override
    public int updateReleaseTime(String orderId, Date releaseTime) {
        int tableIndex = daoSupport.getTableIndex(orderId);
        Map<String, Object> orderIdMap = new HashMap<String, Object>();
        orderIdMap.put("tableIndex", tableIndex);
        orderIdMap.put("orderId", orderId);
        orderIdMap.put("releaseTime", releaseTime);
        SqlSession session = daoSupport.getSqlSession(orderId);
        int affectRows = session.update(namespace + ".updateReleaseTime", orderIdMap);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDORDERINFO, JSON.toJSONString(orderIdMap));
        return affectRows;
    }

    @Override
    public int updateConsumeTime(String orderId, Date consumeTime) {
        int tableIndex = daoSupport.getTableIndex(orderId);
        Map<String, Object> orderIdMap = new HashMap<String, Object>();
        orderIdMap.put("tableIndex", tableIndex);
        orderIdMap.put("orderId", orderId);
        orderIdMap.put("consumeTime", consumeTime);
        SqlSession session = daoSupport.getSqlSession(orderId);
        int affectRows = session.update(namespace + ".updateConsumeTime", orderIdMap);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDORDERINFO, JSON.toJSONString(orderIdMap));
        return affectRows;
    }

    @Override
    public int updateRefundTime(String orderId, Date refundTime) {
        int tableIndex = daoSupport.getTableIndex(orderId);
        Map<String, Object> orderIdMap = new HashMap<String, Object>();
        orderIdMap.put("tableIndex", tableIndex);
        orderIdMap.put("orderId", orderId);
        orderIdMap.put("refundTime", refundTime);
        SqlSession session = daoSupport.getSqlSession(orderId);
        int affectRows = session.update(namespace + ".updateRefundTime", orderIdMap);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDORDERINFO, JSON.toJSONString(orderIdMap));
        return affectRows;
    }

}
