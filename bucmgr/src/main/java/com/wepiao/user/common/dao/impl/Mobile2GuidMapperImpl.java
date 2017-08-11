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
import com.wepiao.user.common.dao.Mobile2GuidMapper;
import com.wepiao.user.common.entry.Mobile2Guid;

@Repository
public class Mobile2GuidMapperImpl implements Mobile2GuidMapper {

    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.Mobile2GuidMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

	@Override
	public int insert(Mobile2Guid record) {
		String mobileNo = record.getMobileNo();
        record.setTableIndex(daoSupport.getTableIndex(mobileNo));
        SqlSession session = daoSupport.getSqlSession(mobileNo);
        int affectRows = session.insert(namespace + ".insert", record);
        logger.info(LogMsg.INSERT_DB_FOR_MOBILE2GUID, JSON.toJSONString(record));
        return affectRows;
	}

	@Override
	public Mobile2Guid queryOneByMobile(String mobileNo) {
		int tableIndex = daoSupport.getTableIndex(mobileNo);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        paramMap.put("mobileNo", mobileNo);
        SqlSession session = daoSupport.getSqlSession(mobileNo);
        Mobile2Guid mobile2Guid = session.selectOne(namespace + ".queryOneByMobile", paramMap);
        return mobile2Guid;
	}

    @Override
    public int deleteByMobileAndGuid(String mobileNo, String guid) {
        int tableIndex = daoSupport.getTableIndex(mobileNo);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        paramMap.put("mobileNo", mobileNo);
        paramMap.put("guid", guid);
        SqlSession session = daoSupport.getSqlSession(mobileNo);
        int affectRows = session.delete(namespace + ".deleteByMobileAndGuid", paramMap);
        logger.info(LogMsg.DELETE_DB_FOR_MOBILE2GUID, paramMap.toString());
        return affectRows;
    }
}
