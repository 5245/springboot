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
import com.wepiao.user.common.dao.IdRelationHistoryMapper;
import com.wepiao.user.common.entry.IdRelationHistory;
import com.wepiao.user.common.entry.enumeration.BindingStatus;

@Repository
public class IdRelationHistoryMapperImpl implements IdRelationHistoryMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.IdRelationHistoryMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insertRelation(IdRelationHistory relation) {
        String parentId = relation.getParentId();
        relation.setTableIndex(daoSupport.getTableIndex(parentId));
        SqlSession session = daoSupport.getSqlSession(parentId);
        int affectRows = session.insert(namespace + ".insertRelation", relation);
        logger.info(LogMsg.INSERT_DB_FOR_ID_RELATION_HISTORY, JSON.toJSONString(relation));
        return affectRows;
    }

    @Override
    public List<IdRelationHistory> queryRelationByParent(String parentId) {
        int tableIndex = daoSupport.getTableIndex(parentId);
        Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put("parentId", parentId);
        idMap.put("tableIndex", tableIndex);
        SqlSession session = daoSupport.getSqlSession(parentId);
        return session.selectList(namespace + ".queryRelationByParent", idMap);
    }

    @Override
    public List<IdRelationHistory> queryRelationByParentAndStatus(String parentId, BindingStatus bindingStatus) {
        int tableIndex = daoSupport.getTableIndex(parentId);
        Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put("parentId", parentId);
        idMap.put("bindingStatus", bindingStatus);
        idMap.put("tableIndex", tableIndex);
        SqlSession session = daoSupport.getSqlSession(parentId);
        return session.selectList(namespace + ".queryRelationByParentAndStatus", idMap);
    }

    @Override
    public int updateBindingStatus(String parentId, String childId, BindingStatus bindingStatus) {
        int tableIndex = daoSupport.getTableIndex(parentId);
        Map<String, Object> statusUpdateMap = new HashMap<String, Object>();
        statusUpdateMap.put("tableIndex", tableIndex);
        statusUpdateMap.put("parentId", parentId);
        statusUpdateMap.put("childId", childId);
        statusUpdateMap.put("bindingStatus", bindingStatus);
        SqlSession session = daoSupport.getSqlSession(parentId);
        int affectRows = session.update(namespace + ".updateBindingStatus", statusUpdateMap);
        logger.info(LogMsg.UPDATE_DB_FOR_ID_RELATION_HISTORY, statusUpdateMap.toString());
        return affectRows;
    }

    /**
     * 更新父节点下面所有用户关系的绑定状态 (先提供给ucmgr删除用户，更新memberId下面所有节点的状态)
     * @param parentId
     * @return
     *
     */
    @Override
    public int updateAllBindingStatus(String parentId, BindingStatus bindingStatus) {
        int tableIndex = daoSupport.getTableIndex(parentId);
        Map<String, Object> statusUpdateMap = new HashMap<String, Object>();
        statusUpdateMap.put("parentId", parentId);
        statusUpdateMap.put("tableIndex", tableIndex);
        statusUpdateMap.put("bindingStatus", bindingStatus);
        SqlSession session = daoSupport.getSqlSession(parentId);
        int affectRows = session.update(namespace + ".updateAllBindingStatus", statusUpdateMap);
        if (0 < affectRows) {
            logger.info(LogMsg.UPDATE_DB_FOR_ID_RELATION_HISTORY, JSON.toJSONString(statusUpdateMap));
        }
        return affectRows;
    }
}
