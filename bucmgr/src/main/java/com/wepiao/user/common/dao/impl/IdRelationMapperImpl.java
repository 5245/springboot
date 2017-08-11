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
import com.wepiao.user.common.dao.IdRelationMapper;
import com.wepiao.user.common.entry.IdRelation;

@Repository
public class IdRelationMapperImpl implements IdRelationMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.IdRelationMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insertRelation(IdRelation relation) {
        String childId = relation.getChildId();
        relation.setTableIndex(daoSupport.getTableIndex(childId));
        SqlSession session = daoSupport.getSqlSession(childId);
        int affectRows = session.insert(namespace + ".insertRelation", relation);
        logger.info(LogMsg.INSERT_DB_FOR_ID_RELATION, JSON.toJSONString(relation));
        return affectRows;
    }

    @Override
    public List<IdRelation> queryRelationByChild(String childId) {
        int tableIndex = daoSupport.getTableIndex(childId);
        Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put("child_id", childId);
        idMap.put("tableIndex", tableIndex);
        SqlSession session = daoSupport.getSqlSession(childId);
        return session.selectList(namespace + ".queryRelationByChild", idMap);
    }

    @Override
    public IdRelation isRelationExisted(IdRelation relation) {
        int tableIndex = daoSupport.getTableIndex(relation.getChildId());
        Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put("child_id", relation.getChildId());
        idMap.put("parent_id", relation.getParentId());
        idMap.put("tableIndex", tableIndex);
        SqlSession session = daoSupport.getSqlSession(relation.getChildId());
        return session.selectOne(namespace + ".isRelationExisted", idMap);
    }

    @Override
    public int deleteRelation(String childId, String parentId) {
        int tableIndex = daoSupport.getTableIndex(childId);
        Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put("child_id", childId);
        idMap.put("parent_id", parentId);
        idMap.put("tableIndex", tableIndex);
        SqlSession session = daoSupport.getSqlSession(childId);
        int affectRows = session.delete(namespace + ".deleteRelation", idMap);
        logger.info(LogMsg.DELETE_DB_FOR_ID_RELATION, idMap.toString());
        return affectRows;
    }
}
