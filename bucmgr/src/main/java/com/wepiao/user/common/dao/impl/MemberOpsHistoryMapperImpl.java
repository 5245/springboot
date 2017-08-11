package com.wepiao.user.common.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.wepiao.user.common.base.dao.SqlSessionMutilSourceDaoSupport;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.dao.MemberOpsHistoryMapper;
import com.wepiao.user.common.entry.MemberOpsHistory;

@Repository
public class MemberOpsHistoryMapperImpl implements MemberOpsHistoryMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.MemberOpsHistoryMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

	@Override
	public int insertMemberOpsHistory(MemberOpsHistory memberOpsHistory) {
		String memberId = memberOpsHistory.getMemberId();
		memberOpsHistory.setTableIndex(daoSupport.getTableIndex(memberId));
        SqlSession session = daoSupport.getSqlSession(memberId);
        int affectRows = session.insert(namespace + ".insertMemberOpsHistory", memberOpsHistory);
        logger.info(LogMsg.INSERT_DB_FOR_MEMBER_OP_HIS, JSON.toJSONString(memberOpsHistory));
        return affectRows;
	}
}
