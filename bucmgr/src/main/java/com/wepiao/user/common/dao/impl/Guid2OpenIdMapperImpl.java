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
import com.wepiao.user.common.dao.Guid2OpenIdMapper;
import com.wepiao.user.common.entry.Guid2OpenId;

@Repository
public class Guid2OpenIdMapperImpl implements Guid2OpenIdMapper {

    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.Guid2OpenIdMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

	@Override
	public int insert(Guid2OpenId record) {
		String guid = record.getGuid();
        record.setTableIndex(daoSupport.getTableIndex(guid));
        SqlSession session = daoSupport.getSqlSession(guid);
        int affectRows = session.insert(namespace + ".insert", record);
        logger.info(LogMsg.INSERT_DB_FOR_GUID2OPENID, JSON.toJSONString(record));
        return affectRows;
	}

	@Override
	public List<Guid2OpenId> queryAllByGuid(String guid) {
		int tableIndex = daoSupport.getTableIndex(guid);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        paramMap.put("guid", guid);
        SqlSession session = daoSupport.getSqlSession(guid);
        List<Guid2OpenId> list = session.selectList(namespace + ".queryAllByGuid", paramMap);
        return list;
	}

}
