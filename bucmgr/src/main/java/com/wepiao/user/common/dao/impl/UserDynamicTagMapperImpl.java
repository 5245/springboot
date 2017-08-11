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
import com.alibaba.fastjson.TypeReference;
import com.wepiao.user.common.base.dao.SqlSessionMutilSourceDaoSupport;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.dao.UserDynamicTagMapper;
import com.wepiao.user.common.entry.UserDynamicTag;
import com.wepiao.user.common.entry.enumeration.OtherID;

@Repository
public class UserDynamicTagMapperImpl implements UserDynamicTagMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.UserDynamicTagMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public UserDynamicTag queryUserDynamicTagById(String id, OtherID idType) {
        int tableIndex = daoSupport.getTableIndex(id);
        Map<String, Object> uTagMap = new HashMap<String, Object>();
        uTagMap.put("tableIndex", tableIndex);
        uTagMap.put("id", id);
        uTagMap.put("idType", idType);
        SqlSession session = daoSupport.getSqlSession(id);
        UserDynamicTag userDynamicTag = (UserDynamicTag) session.selectOne(namespace + ".queryUserDynamicTagById", uTagMap);
        return userDynamicTag;
    }

    @Override
    public int deleteUserDynamicTag(String id, OtherID idType) {
        int tableIndex = daoSupport.getTableIndex(id);
        Map<String, Object> uTagMap = new HashMap<String, Object>();
        uTagMap.put("tableIndex", tableIndex);
        uTagMap.put("id", id);
        uTagMap.put("idType", idType);
        SqlSession session = daoSupport.getSqlSession(id);
        int affectRows = session.delete(namespace + ".deleteUserDynamicTag", uTagMap);
        logger.info(LogMsg.DELETE_DB_FOR_USER_DYNAMIC_TAG, uTagMap.toString());
        return affectRows;
    }

    @Override
    public int insertUserDynamicTag(UserDynamicTag userDynamicTag) {
        String id = userDynamicTag.getId();
        userDynamicTag.setTableIndex(daoSupport.getTableIndex(id));
        SqlSession session = daoSupport.getSqlSession(id);
        int affectRows = session.insert(namespace + ".insertUserDynamicTag", userDynamicTag);
        logger.info(LogMsg.INSERT_DB_FOR_USER_DYNAMIC_TAG, JSON.toJSONString(userDynamicTag));
        return affectRows;
    }

    @Override
    public int updateUserDynamicTag(UserDynamicTag userDynamicTag) {
        String id = userDynamicTag.getId();
        userDynamicTag.setTableIndex(daoSupport.getTableIndex(id));
        SqlSession session = daoSupport.getSqlSession(id);
        int affectRows = session.update(namespace + ".updateUserDynamicTag", userDynamicTag);
        logger.info(LogMsg.UPDATE_DB_FOR_USER_DYNAMIC_TAG, JSON.toJSONString(userDynamicTag));
        return affectRows;
    }

    /**
     * queryDynamicUserTag4Warm: <br/>
     * 预热分批查询用户动态标签数据<br/>
     * @author liujie
     * @param dbIndex
     * @param tableIndex
     * @param perNo
     * @param perSize
     * @return
     */
    private List<UserDynamicTag> queryDynamicUserTag4Warm(int dbIndex, int tableIndex, int perNo, int perSize) {
        Map<String, Object> uTagMap = new HashMap<String, Object>();
        uTagMap.put("tableIndex", tableIndex);
        uTagMap.put("start", perNo * perSize);
        uTagMap.put("size", perSize);
        SqlSession session = daoSupport.getSqlSessionList()[dbIndex];
        return session.selectList(namespace + ".queryUserDynamicTag4Warm", uTagMap);
    }

    /**
     * 预热所有用户动态标签数据到redis
     * @see com.wepiao.uctemp.dao.UserDynamicTagMapper#warmAllDynamicTagData()
     */
    @Override
    public void warmAllDynamicTagData() {
        //当前数据库总数
        int dbCount = daoSupport.getDbCount();
        //当前单库表个数(128)
        int tableCount = daoSupport.getTableCount();

        for (int i = 0; i < dbCount; i++) {
            int tableStart = i * tableCount;
            int tableEnd = (i + 1) * tableCount;
            for (int j = tableStart; j < tableEnd; j++) {
                List<UserDynamicTag> userTagList = null;
                int k = 0;
                do {
                    userTagList = queryDynamicUserTag4Warm(i, j, k, 1000);
                    if (null != userTagList) {
                        //遍历写入redis
                        for (UserDynamicTag userTag : userTagList) {
                            String id = userTag.getId();
                            OtherID idType = userTag.getIdType();
                            String tag = userTag.getTag();
                            try {
                                Map<String, String> tagMap = JSON.parseObject(tag, new TypeReference<Map<String, String>>() {});
                                if (null != tagMap && tagMap.size() > 0) {
                                    //写入redis
                                    //RedisMapper.setDynamicUserTagAll(id, idType, tagMap, System.currentTimeMillis() / 1000L, 0);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.info("error happend on handler user dynamic tag! id=" + id + ",idType=" + idType + ",tag=" + tag);
                            }
                        }
                    }
                    k++;
                    logger.info("load db=" + i + ",table=" + j + ",batch=" + k + ",to redis done!");
                } while (null != userTagList && 0 < userTagList.size());
            }
        }
    }
}
