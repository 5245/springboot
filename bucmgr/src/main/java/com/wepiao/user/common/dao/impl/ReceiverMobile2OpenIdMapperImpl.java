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
import com.wepiao.user.common.dao.ReceiverMobile2OpenIdMapper;
import com.wepiao.user.common.entry.ReceiverMobile2OpenId;

@Repository
public class ReceiverMobile2OpenIdMapperImpl implements ReceiverMobile2OpenIdMapper {

    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.ReceiverMobile2OpenIdMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

	@Override
	public int insert(ReceiverMobile2OpenId record) {
		String mobileNo = record.getMobileNo();
        record.setTableIndex(daoSupport.getTableIndex(mobileNo));
        SqlSession session = daoSupport.getSqlSession(mobileNo);
        int affectRows = session.insert(namespace + ".insert", record);
        logger.info(LogMsg.INSERT_DB_FOR_RECVMOBILE2OPENID, JSON.toJSONString(record));
        return affectRows;
	}

	@Override
	public List<ReceiverMobile2OpenId> queryAllByMobile(String mobileNo) {
		int tableIndex = daoSupport.getTableIndex(mobileNo);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        paramMap.put("mobileNo", mobileNo);
        SqlSession session = daoSupport.getSqlSession(mobileNo);
        List<ReceiverMobile2OpenId> list = session.selectList(namespace + ".queryAllByMobile", paramMap);
        return list;
	}

	@Override
	public ReceiverMobile2OpenId queryOneByMobileAndOpenId(String mobileNo, String openId) {
		int tableIndex = daoSupport.getTableIndex(mobileNo);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableIndex", tableIndex);
        paramMap.put("mobileno", mobileNo);
        paramMap.put("openId", openId);
        SqlSession session = daoSupport.getSqlSession(openId);
        ReceiverMobile2OpenId receiverMobile2OpenId = session.selectOne(namespace + ".queryOneByMobileAndOpenId", paramMap);
        return receiverMobile2OpenId;
	}
}
