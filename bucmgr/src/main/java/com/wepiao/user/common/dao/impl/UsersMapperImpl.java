package com.wepiao.user.common.dao.impl;

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
import com.wepiao.user.common.dao.UsersMapper;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.Status;

@Repository
public class UsersMapperImpl implements UsersMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.UsersMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(Users user) {
        Integer uid = user.getUid();
        user.setTableIndex(daoSupport.getTableIndex(uid));
        SqlSession session = daoSupport.getSqlSession(uid);
        int affectRows = session.insert(namespace + ".insert", user);
        logger.info(LogMsg.INSERT_DB_FOR_USER, JSON.toJSONString(user));
        return affectRows;
    }

    @Override
    public int update(Users user) {
        Integer uid = user.getUid();
        user.setTableIndex(daoSupport.getTableIndex(uid));
        SqlSession session = daoSupport.getSqlSession(uid);
        int affectRows = session.update(namespace + ".update", user);
        logger.info(LogMsg.UPDATE_DB_FOR_USER, JSON.toJSONString(user));
        return affectRows;
    }

    @Override
    public Users queryOneByUid(int uid) {
        int tableIndex = daoSupport.getTableIndex(uid);
        Map<String, Object> uidMap = new HashMap<String, Object>();
        uidMap.put("tableIndex", tableIndex);
        uidMap.put("uid", uid);
        SqlSession session = daoSupport.getSqlSession(uid);
        Users user = (Users) session.selectOne(namespace + ".queryOneByUid", uidMap);
        return user;
    }

    @Override
    public int delete(int uid) {
        int tableIndex = daoSupport.getTableIndex(uid);
        Map<String, Object> uidMap = new HashMap<String, Object>();
        uidMap.put("tableIndex", tableIndex);
        uidMap.put("uid", uid);
        SqlSession session = daoSupport.getSqlSession(uid);
        int affectRows = session.update(namespace + ".delete", uidMap);
        logger.info(LogMsg.DELETE_DB_FOR_USER, JSON.toJSONString(uidMap));
        return affectRows;
    }

    @Override
    public int updatePwd(int uid, String password) {
        int tableIndex = daoSupport.getTableIndex(uid);
        Map<String, Object> pwdMap = new HashMap<String, Object>();
        pwdMap.put("tableIndex", tableIndex);
        pwdMap.put("uid", uid);
        pwdMap.put("password", password);
        SqlSession session = daoSupport.getSqlSession(uid);
        int affectRows = session.update(namespace + ".updatePwd", pwdMap);
        logger.info(LogMsg.UPDATE_DB_FOR_USER, JSON.toJSONString(pwdMap));
        return affectRows;
    }

    @Override
    public Users queryOneByMobileNo(String mobileNo) {
        int tableCount = daoSupport.getTableCount();
        SqlSession[] seesions = daoSupport.getSqlSessionList();
        if (seesions.length != 0) {
            int totalCount = tableCount * seesions.length;
            for (int i = 0; i < totalCount; i++) {
                Map<String, Object> mobileNoMap = new HashMap<String, Object>();
                mobileNoMap.put("tableIndex", i);
                mobileNoMap.put("mobileNo", mobileNo);
                int dbIndex = i / tableCount;
                Users user = (Users) daoSupport.getSqlSessionByIndex(dbIndex).selectOne(namespace + ".queryOneByMobileNo", mobileNoMap);
                if (user != null) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public int updateStatus(int uid, Status status) {
        int tableIndex = daoSupport.getTableIndex(uid);
        Map<String, Object> statusMap = new HashMap<String, Object>();
        statusMap.put("tableIndex", tableIndex);
        statusMap.put("uid", uid);
        statusMap.put("status", status);
        SqlSession session = daoSupport.getSqlSession(uid);
        int affectRows = session.update(namespace + ".updateStatus", statusMap);
        logger.info(LogMsg.UPDATE_DB_FOR_USER, JSON.toJSONString(statusMap));
        return affectRows;
    }

    @Override
    public int updateCinemaFavorites(int uid, String cinemaFavorites) {
        int tableIndex = daoSupport.getTableIndex(uid);
        Map<String, Object> cinemaFavoritesMap = new HashMap<String, Object>();
        cinemaFavoritesMap.put("tableIndex", tableIndex);
        cinemaFavoritesMap.put("uid", uid);
        cinemaFavoritesMap.put("cinemaFavorites", cinemaFavorites);
        SqlSession session = daoSupport.getSqlSession(uid);
        int affectRows = session.update(namespace + ".updateCinemaFavorites", cinemaFavoritesMap);
        logger.info(LogMsg.UPDATE_DB_FOR_USER, JSON.toJSONString(cinemaFavoritesMap));
        return affectRows;
    }
}
