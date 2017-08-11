/**
 * Project Name:uc-common
 * File Name:PrivateVipInfoHistoryMapperImpl.java
 * Package Name:com.wepiao.user.common.dao.impl
 * Date:2016年12月1日下午4:39:39
 *
*/

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
import com.wepiao.user.common.dao.PrivateVipInfoHistoryMapper;
import com.wepiao.user.common.entry.PrivateVipInfoHistory;
import com.wepiao.user.common.entry.enumeration.PrivateVipStatus;

/**
 * ClassName:PrivateVipInfoHistoryMapperImpl <br/>
 * Function: 私有vip历史信息DB辅助类 <br/>
 * Date:     2016年12月1日 下午4:39:39 <br/>
 * @author   liujie
 * @version  
 * @see 	 
 */
@Repository
public class PrivateVipInfoHistoryMapperImpl implements PrivateVipInfoHistoryMapper {

    private static Logger                   logger    = LoggerFactory.getLogger(PrivateVipInfoHistoryMapperImpl.class);
    private static String                   namespace = "com.wepiao.user.common.dao.PrivateVipInfoHistoryMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(PrivateVipInfoHistory privateVipInfoHistory) {
        Integer memberId = privateVipInfoHistory.getMemberId();
        privateVipInfoHistory.setTableIndex(daoSupport.getTableIndex(memberId));
        SqlSession session = daoSupport.getSqlSession(memberId);
        int affectRows = session.insert(namespace + ".insert", privateVipInfoHistory);
        logger.info(LogMsg.INSERT_DB_FOR_PRIVATE_VIP_INFO_HISTORY, JSON.toJSONString(privateVipInfoHistory));
        return affectRows;
    }

    @Override
    public PrivateVipInfoHistory queryAvailablePrivateVipByMemberId(Integer memberId, PrivateVipStatus status) {
        Map<String, Object> memberIdMap = new HashMap<String, Object>();
        memberIdMap.put("memberId", memberId);
        memberIdMap.put("vipStatus", status);
        memberIdMap.put("tableIndex", daoSupport.getTableIndex(memberId));
        SqlSession session = daoSupport.getSqlSession(memberId);
        PrivateVipInfoHistory privateVipInfoHistory = (PrivateVipInfoHistory) session.selectOne(namespace + ".queryAvailablePrivateVipByMemberId", memberIdMap);
        return privateVipInfoHistory;
    }

    @Override
    public int update(PrivateVipInfoHistory privateVipInfoHistory) {
        Integer memberId = privateVipInfoHistory.getMemberId();
        privateVipInfoHistory.setTableIndex(daoSupport.getTableIndex(memberId));
        SqlSession session = daoSupport.getSqlSession(memberId);
        int affectRows = session.insert(namespace + ".update", privateVipInfoHistory);
        logger.info(LogMsg.UPDATE_DB_FOR_PRIVATE_VIP_INFO_HISTORY, JSON.toJSONString(privateVipInfoHistory));
        return affectRows;
    }

}
