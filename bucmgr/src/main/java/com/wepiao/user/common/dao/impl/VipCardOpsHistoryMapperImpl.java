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
import com.wepiao.user.common.dao.VipCardOpsHistoryMapper;
import com.wepiao.user.common.entry.VipCardOpsHistory;

@Repository
public class VipCardOpsHistoryMapperImpl implements VipCardOpsHistoryMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.VipCardOpsHistoryMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(VipCardOpsHistory vipCardOpsHistory) {
        Integer memberId = vipCardOpsHistory.getMemberId();
        vipCardOpsHistory.setTableIndex(daoSupport.getTableIndex(memberId));
        SqlSession session = daoSupport.getSqlSession(memberId);
        int affectRows = session.insert(namespace + ".insert", vipCardOpsHistory);
        logger.info(LogMsg.INSERT_DB_FOR_VIPCARDOPSHISTORY, JSON.toJSONString(vipCardOpsHistory));
        return affectRows;
    }

    @Override
    public List<VipCardOpsHistory> queryOpsHistoryByMemberId(String memberId) {
        int tableIndex = daoSupport.getTableIndex(memberId);
        Map<String, Object> memberIdMap = new HashMap<String, Object>();
        memberIdMap.put("tableIndex", tableIndex);
        memberIdMap.put("memberId", memberId);
        SqlSession session = daoSupport.getSqlSession(memberId);
        return session.selectList(namespace + ".queryOpsHistoryByMemberId", memberIdMap);
    }

}
