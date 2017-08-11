package com.wepiao.user.common.dao.impl;

import java.util.Date;
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
import com.wepiao.user.common.dao.VipCardInfoMapper;
import com.wepiao.user.common.entry.VipCardInfo;

@Repository
public class VipCardInfoMapperImpl implements VipCardInfoMapper {
    private static Logger                   logger    = LoggerFactory.getLogger(VipCardInfoMapperImpl.class);
    private static String                   namespace = "com.wepiao.user.common.dao.VipCardInfoMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(VipCardInfo vipCardInfo) {
        String cardNo = vipCardInfo.getCardNo();
        vipCardInfo.setTableIndex(daoSupport.getTableIndex(cardNo));
        SqlSession session = daoSupport.getSqlSession(cardNo);
        int affectRows = session.insert(namespace + ".insert", vipCardInfo);
        logger.info(LogMsg.INSERT_DB_FOR_VIPCARDINFO, JSON.toJSONString(vipCardInfo));
        return affectRows;
    }

    @Override
    public int delete(String cardNo) {
        int tableIndex = daoSupport.getTableIndex(cardNo);
        Map<String, Object> cardNoMap = new HashMap<String, Object>();
        cardNoMap.put("tableIndex", tableIndex);
        cardNoMap.put("cardNo", cardNo);
        SqlSession session = daoSupport.getSqlSession(cardNo);
        int affectRows = session.update(namespace + ".delete", cardNoMap);
        logger.info(LogMsg.DELETE_DB_FOR_VIPCARDINFO, JSON.toJSONString(cardNoMap));
        return affectRows;
    }

    @Override
    public int update(VipCardInfo vipCardInfo) {
        String cardNo = vipCardInfo.getCardNo();
        vipCardInfo.setTableIndex(daoSupport.getTableIndex(cardNo));
        SqlSession session = daoSupport.getSqlSession(cardNo);
        int affectRows = session.update(namespace + ".update", vipCardInfo);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDINFO, JSON.toJSONString(vipCardInfo));
        return affectRows;

    }

    @Override
    public VipCardInfo queryOneByCardNo(String cardNo) {
        int tableIndex = daoSupport.getTableIndex(cardNo);
        Map<String, Object> cardNoMap = new HashMap<String, Object>();
        cardNoMap.put("tableIndex", tableIndex);
        cardNoMap.put("cardNo", cardNo);
        SqlSession session = daoSupport.getSqlSession(cardNo);
        VipCardInfo vcOrderInfo = (VipCardInfo) session.selectOne(namespace + ".queryOneByCardNo", cardNoMap);
        return vcOrderInfo;
    }

    @Override
    public int updateTotalUsed(String cardNo, int totalUsed) {
        int tableIndex = daoSupport.getTableIndex(cardNo);
        Map<String, Object> cardNoMap = new HashMap<String, Object>();
        cardNoMap.put("tableIndex", tableIndex);
        cardNoMap.put("cardNo", cardNo);
        cardNoMap.put("totalUsed", totalUsed);
        SqlSession session = daoSupport.getSqlSession(cardNo);
        int affectRows = session.update(namespace + ".updateTotalUsed", cardNoMap);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDINFO, JSON.toJSONString(cardNoMap));
        return affectRows;
    }

    @Override
    public int updateUpdateTime(String cardNo, Date updateTime) {
        int tableIndex = daoSupport.getTableIndex(cardNo);
        Map<String, Object> cardNoMap = new HashMap<String, Object>();
        cardNoMap.put("tableIndex", tableIndex);
        cardNoMap.put("cardNo", cardNo);
        cardNoMap.put("updateTime", updateTime);
        SqlSession session = daoSupport.getSqlSession(cardNo);
        int affectRows = session.update(namespace + ".updateUpdateTime", cardNoMap);
        logger.info(LogMsg.UPDATE_DB_FOR_VIPCARDINFO, JSON.toJSONString(cardNoMap));
        return affectRows;
    }

    @Override
    public List<VipCardInfo> queryAllVipCardInfo(int dbIndex,int tableIndex) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        SqlSession session = daoSupport.getSqlSessionList()[dbIndex];
        return session.selectList(namespace+".queryAllVipCardInfo", paramMap);
    }

}
