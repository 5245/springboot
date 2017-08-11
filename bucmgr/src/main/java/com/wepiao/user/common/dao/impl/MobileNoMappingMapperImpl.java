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
import com.wepiao.user.common.dao.MobileNoMappingMapper;
import com.wepiao.user.common.entry.MobileNoMapping;

@Repository
public class MobileNoMappingMapperImpl implements MobileNoMappingMapper {

    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.MobileNoMappingMapper";
    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(MobileNoMapping mobileNoMapping) {
        String mobileNo = mobileNoMapping.getMobileNo();
        mobileNoMapping.setTableIndex(daoSupport.getTableIndex(mobileNo));
        SqlSession session = daoSupport.getSqlSession(mobileNo);
        int affectRows = session.insert(namespace + ".insert", mobileNoMapping);
        logger.info(LogMsg.INSERT_DB_FOR_MOBILE2UID, JSON.toJSONString(mobileNoMapping));
        return affectRows;
    }

    @Override
    public MobileNoMapping queryOneByMobileNo(String mobileNo) {
        int tableIndex = daoSupport.getTableIndex(mobileNo);
        Map<String, Object> mobileMap = new HashMap<String, Object>();
        mobileMap.put("tableIndex", tableIndex);
        mobileMap.put("mobileNo", mobileNo);
        SqlSession session = daoSupport.getSqlSession(mobileNo);
        return (MobileNoMapping) session.selectOne(namespace + ".queryOneByMobileNo", mobileMap);
    }

    @Override
    public int delete(String mobileNo) {
        int tableIndex = daoSupport.getTableIndex(mobileNo);
        Map<String, Object> mobileMap = new HashMap<String, Object>();
        mobileMap.put("tableIndex", tableIndex);
        mobileMap.put("mobileNo", mobileNo);
        SqlSession session = daoSupport.getSqlSession(mobileNo);
        int affectRows = session.delete(namespace + ".delete", mobileMap);
        logger.info(LogMsg.DELETE_DB_FOR_MOBILE2UID, JSON.toJSONString(mobileMap));
        return affectRows;
    }
}
