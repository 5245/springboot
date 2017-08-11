package com.wepiao.user.common.dao.impl;

import java.util.ArrayList;
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
import com.wepiao.user.common.dao.OpenIdInfoMapper;
import com.wepiao.user.common.entry.OpenId;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;

@Repository
public class OpenIdInfoMapperImpl implements OpenIdInfoMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.OpenIdInfoMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(OpenIdInfo openIdInfo) {
        String openId = openIdInfo.getOpenId();
        openIdInfo.setTableIndex(daoSupport.getTableIndex(openId));
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.insert(namespace + ".insert", openIdInfo);
        logger.info(LogMsg.INSERT_DB_FOR_OPENID, JSON.toJSONString(openIdInfo));
        return affectRows;
    }

    @Override
    public int update(OpenIdInfo openIdInfo) {
        String openId = openIdInfo.getOpenId();
        openIdInfo.setTableIndex(daoSupport.getTableIndex(openId));
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.update(namespace + ".update", openIdInfo);
        logger.info(LogMsg.UPDATE_DB_FOR_OPENID, JSON.toJSONString(openIdInfo));
        return affectRows;
    }

    @Override
    public int delete(String openId, OtherID otherId) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> deleteMap = new HashMap<String, Object>();
        deleteMap.put("tableIndex", tableIndex);
        deleteMap.put("OpenID", openId);
        deleteMap.put("OtherID", otherId);
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.update(namespace + ".delete", deleteMap);
        logger.info(LogMsg.DELETE_DB_FOR_OPENID, JSON.toJSONString(deleteMap));
        return affectRows;
    }

    @Override
    public int updateStatus(String openId, OtherID otherId, Status status) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> statusUpdateMap = new HashMap<String, Object>();
        statusUpdateMap.put("tableIndex", tableIndex);
        statusUpdateMap.put("OpenID", openId);
        statusUpdateMap.put("OtherID", otherId);
        statusUpdateMap.put("status", status);
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.update(namespace + ".updateStatus", statusUpdateMap);
        logger.info(LogMsg.UPDATE_DB_FOR_OPENID, statusUpdateMap.toString());
        return affectRows;
    }

    @Override
    public int updateBindingStatus(String openId, OtherID otherId, BindingStatus status) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> statusUpdateMap = new HashMap<String, Object>();
        statusUpdateMap.put("tableIndex", tableIndex);
        statusUpdateMap.put("OpenID", openId);
        statusUpdateMap.put("OtherID", otherId);
        statusUpdateMap.put("bindingStatus", status);
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.update(namespace + ".updateBindingStatus", statusUpdateMap);
        logger.info(LogMsg.UPDATE_DB_FOR_OPENID, statusUpdateMap.toString());
        return affectRows;
    }

    @Override
    public int updateCinemaFavorites(String openId, OtherID otherId, String cinemaFavorites) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> cinemaUpdateMap = new HashMap<String, Object>();
        cinemaUpdateMap.put("tableIndex", tableIndex);
        cinemaUpdateMap.put("OpenID", openId);
        cinemaUpdateMap.put("OtherID", otherId);
        cinemaUpdateMap.put("cinemaFavorites", cinemaFavorites);
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.update(namespace + ".updateCinemaFavorites", cinemaUpdateMap);
        logger.info(LogMsg.UPDATE_DB_FOR_OPENID, cinemaUpdateMap.toString());
        return affectRows;
    }

    @Override
    public OpenIdInfo queryOneByOpenId(String openId) {
        int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> openIdMap = new HashMap<String, Object>();
        openIdMap.put("OpenID", openId);
        openIdMap.put("tableIndex", tableIndex);
        SqlSession session = daoSupport.getSqlSession(openId);
        return (OpenIdInfo) session.selectOne(namespace + ".queryOneByOpenId", openIdMap);
    }

    @Override
    public List<OpenId> queryBlackList() {
        List<OpenId> result = new ArrayList<OpenId>();
        List<AsyncReader> readerList = new ArrayList<AsyncReader>();
        int tableCount = daoSupport.getTableCount();
        SqlSession[] seesions = daoSupport.getSqlSessionList();
        if (seesions.length != 0) {
            // 建N个线程，N=数据库分库数
            int totalCount = tableCount * seesions.length;
            for (int i = 0; i < totalCount; i++) {
                int dbIndex = i / tableCount;
                AsyncReader reader = new AsyncReader(i, dbIndex);
                readerList.add(reader);
                Thread t = new Thread(reader);
                t.start();
            }

            int finishedCount = 0;
            int totalFinished = readerList.size();
            while (finishedCount < totalFinished) {
                for (int i = 0; i < readerList.size(); i++) {
                    if (readerList.get(i).isRead) {
                        finishedCount++;
                        result.addAll(readerList.get(i).result);
                        readerList.remove(i);
                    }
                }
            }
        }
        return result;
    }

    class AsyncReader implements Runnable {

        private int          tableIndex;
        private int          dbIndex;
        private List<OpenId> result = new ArrayList<OpenId>();
        private boolean      isRead = false;

        public AsyncReader(int tableIndex, int dbIndex) {
            super();
            this.tableIndex = tableIndex;
            this.dbIndex = dbIndex;
        }

        @Override
        public void run() {
            List<OpenId> tmpList = daoSupport.getSqlSessionByIndex(dbIndex).selectList(namespace + ".queryBlackList", tableIndex);
            if (tmpList != null) {
                this.result.addAll(tmpList);
            }
            isRead = true;
        }
    }

    @Override
    public List<OpenIdInfo> queryOpenIdInfoList(int dbIndex,int tableIndex,int perNo,int perSize,Date lastLoginTime) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        paramMap.put("lastLoginTime", lastLoginTime);
        paramMap.put("start", perNo*perSize);
        paramMap.put("size", perSize);
        SqlSession session = daoSupport.getSqlSessionList()[dbIndex];
        return session.selectList(namespace+".queryOpenIdInfoList", paramMap);
    }
}
