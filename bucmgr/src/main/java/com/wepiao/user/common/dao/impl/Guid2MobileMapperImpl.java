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
import com.wepiao.user.common.dao.Guid2MobileMapper;
import com.wepiao.user.common.entry.Guid2Mobile;
import com.wepiao.user.common.entry.OpenId2ReceiverMobile;

@Repository
public class Guid2MobileMapperImpl implements Guid2MobileMapper {

    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.Guid2MobileMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

	@Override
	public int insert(Guid2Mobile record) {
		String guid = record.getGuid();
        record.setTableIndex(daoSupport.getTableIndex(guid));
        SqlSession session = daoSupport.getSqlSession(guid);
        int affectRows = session.insert(namespace + ".insert", record);
        logger.info(LogMsg.INSERT_DB_FOR_GUID2MOBILE, JSON.toJSONString(record));
        return affectRows;
	}

	@Override
	public int updateByGuidAndId(Guid2Mobile record) {
	    String guid = record.getGuid();
	    record.setTableIndex(daoSupport.getTableIndex(guid));
	    SqlSession session = daoSupport.getSqlSession(guid);
	    int affectRows = session.update(namespace + ".updateByGuidAndId", record);
	    logger.info(LogMsg.UPDATE_DB_FOR_GUID2MOBILE, JSON.toJSONString(record));
	    return affectRows;
	}
	
	@Override
	public Guid2Mobile queryOneByGuid(String guid) {
		int tableIndex = daoSupport.getTableIndex(guid);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        paramMap.put("guid", guid);
        SqlSession session = daoSupport.getSqlSession(guid);
        Guid2Mobile guid2Mobile = session.selectOne(namespace + ".queryOneByGuid", paramMap);
        return guid2Mobile;
	}
}
