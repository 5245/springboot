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
import com.wepiao.user.common.dao.OpenId2GuidMapper;
import com.wepiao.user.common.entry.Guid2OpenId;
import com.wepiao.user.common.entry.OpenId2Guid;

@Repository
public class OpenId2GuidMapperImpl implements OpenId2GuidMapper {

    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.OpenId2GuidMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

	@Override
	public int insert(OpenId2Guid record) {
		String openId = record.getOpenId();
        record.setTableIndex(daoSupport.getTableIndex(openId));
        SqlSession session = daoSupport.getSqlSession(openId);
        int affectRows = session.insert(namespace + ".insert", record);
        logger.info(LogMsg.INSERT_DB_FOR_OPENID2GUID, JSON.toJSONString(record));
        return affectRows;
	}

	@Override
	public OpenId2Guid queryAllByOpenId(String openId) {
		int tableIndex = daoSupport.getTableIndex(openId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        paramMap.put("openId", openId);
        SqlSession session = daoSupport.getSqlSession(openId);
        OpenId2Guid list = session.selectOne(namespace + ".queryAllByOpenId", paramMap);
        return list;
	}

    
}
