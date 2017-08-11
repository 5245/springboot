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
import com.wepiao.user.common.dao.UserTagMapper;
import com.wepiao.user.common.entry.UserTag;
import com.wepiao.user.common.entry.enumeration.OtherID;

@Repository
public class UserTagMapperImpl implements UserTagMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.UserTagMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public UserTag queryUserTagById(String id, OtherID idType) {
        int tableIndex = daoSupport.getTableIndex(id);
        Map<String, Object> uTagMap = new HashMap<String, Object>();
        uTagMap.put("tableIndex", tableIndex);
        uTagMap.put("id", id);
        uTagMap.put("idType", idType);
        SqlSession session = daoSupport.getSqlSession(id);
        UserTag userTag = (UserTag) session.selectOne(namespace + ".queryUserTagById", uTagMap);
        return userTag;
    }

    @Override
    public int deleteUserTag(String id, OtherID idType) {
        int tableIndex = daoSupport.getTableIndex(id);
        Map<String, Object> uTagMap = new HashMap<String, Object>();
        uTagMap.put("tableIndex", tableIndex);
        uTagMap.put("id", id);
        uTagMap.put("idType", idType);
        SqlSession session = daoSupport.getSqlSession(id);
        int affectRows = session.delete(namespace + ".deleteUserTag", uTagMap);
        logger.info(LogMsg.DELETE_DB_FOR_USER_TAG, uTagMap.toString());
        return affectRows;
    }

    @Override
    public int insertUserTag(UserTag userTag) {
        String id = userTag.getId();
        userTag.setTableIndex(daoSupport.getTableIndex(id));
        SqlSession session = daoSupport.getSqlSession(id);
        int affectRows = session.insert(namespace + ".insertUserTag", userTag);
        logger.info(LogMsg.INSERT_DB_FOR_USER_TAG, JSON.toJSONString(userTag));
        return affectRows;
    }

    @Override
    public int insertSynUserTag(UserTag userTag) {
        String id = userTag.getId();
        userTag.setTableIndex(daoSupport.getTableIndex(id));
        SqlSession session = daoSupport.getSqlSession(id);
        int affectRows = session.insert(namespace + ".insertSynUserTag", userTag);
        logger.info(LogMsg.INSERT_DB_FOR_USER_TAG, JSON.toJSONString(userTag));
        return affectRows;
    }

    @Override
    public int updateUserTag(UserTag userTag) {
        String id = userTag.getId();
        userTag.setTableIndex(daoSupport.getTableIndex(id));
        SqlSession session = daoSupport.getSqlSession(id);
        int affectRows = session.update(namespace + ".updateUserTag", userTag);
        logger.info(LogMsg.UPDATE_DB_FOR_USER_TAG, JSON.toJSONString(userTag));
        return affectRows;
    }

    /**
     * queryStaticUserTag4Warm: <br/>
     * 预热分批次查询用户静态标签数据 <br/>
     * @author liujie
     * @param dbIndex
     * @param tableIndex
     * @param perNo
     * @param perSize
     * @return
     */
    @Override
    public List<UserTag> queryStaticUserTag4Warm(int dbIndex,int tableIndex,int perNo,int perSize){
      Map<String, Object> uTagMap = new HashMap<String, Object>();
      uTagMap.put("tableIndex", tableIndex);
      uTagMap.put("start", perNo*perSize);
      uTagMap.put("size", perSize);
      SqlSession session = daoSupport.getSqlSessionList()[dbIndex];
      return session.selectList(namespace+".queryUserTag4Warm", uTagMap);
    }

}
