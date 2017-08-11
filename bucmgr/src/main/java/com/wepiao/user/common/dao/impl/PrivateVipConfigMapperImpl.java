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
import com.wepiao.user.common.dao.PrivateVipConfigMapper;
import com.wepiao.user.common.entry.PrivateVipConfig;

@Repository
public class PrivateVipConfigMapperImpl implements PrivateVipConfigMapper {
    private static Logger                   logger    = LoggerFactory.getLogger(PrivateVipConfigMapperImpl.class);
    private static String                   namespace = "com.wepiao.user.common.dao.PrivateVipConfigMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(PrivateVipConfig privateVipConfig) {
        SqlSession session = daoSupport.getSqlSessionsIdBackup();
        int affectRows = session.insert(namespace + ".insert", privateVipConfig);
        logger.info(LogMsg.INSERT_DB_FOR_PRIVATE_VIP_CONFIG, JSON.toJSONString(privateVipConfig));
        return affectRows;
    }

    @Override
    public int update(PrivateVipConfig privateVipConfig) {
        SqlSession session = daoSupport.getSqlSessionsIdBackup();
        int affectRows = session.update(namespace + ".update", privateVipConfig);
        logger.info(LogMsg.UPDATE_DB_FOR_PRIVATE_VIP_CONFIG, JSON.toJSONString(privateVipConfig));
        return affectRows;
    }

    @Override
    public PrivateVipConfig queryOneByVipId(Integer vipId) {
        Map<String, Object> vipIdMap = new HashMap<String, Object>();
        vipIdMap.put("vipId", vipId);
        SqlSession session = daoSupport.getSqlSessionsIdBackup();
        PrivateVipConfig privateVipConfig = (PrivateVipConfig) session.selectOne(namespace + ".queryOneByVipId", vipIdMap);
        return privateVipConfig;
    }

    @Override
    public List<PrivateVipConfig> queryAllPrivateVipConfig() {
        SqlSession session = daoSupport.getSqlSessionsIdBackup();
        return session.selectList(namespace+".queryAllPrivateVipConfig");
    }
}
