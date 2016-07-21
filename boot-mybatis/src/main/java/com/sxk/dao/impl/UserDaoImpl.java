package com.sxk.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sxk.base.dao.SqlSessionMutilSourceDaoSupport;
import com.sxk.dao.UserDao;
import com.sxk.model.Users;

@Repository
public class UserDaoImpl implements UserDao {

    private Logger                          logger    = LoggerFactory.getLogger(getClass());

    private String                          namespace = "com.sxk.dao.UserDao";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public Users queryOneByUid() {
        Map<String, Object> uidMap = new HashMap<String, Object>();
        uidMap.put("tableIndex", 0);
        uidMap.put("uid", 103);
        SqlSession session = daoSupport.getSqlSessionLegacyUsers();
        Users user = (Users) session.selectOne(namespace + ".queryOneByUid", uidMap);
        return user;
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
}
