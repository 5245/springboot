package com.wepiao.admin.user.service.handler.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.wepiao.admin.user.service.handler.UserTagHandler;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.dao.UserDynamicTagMapper;
import com.wepiao.user.common.dao.UserTagMapper;
import com.wepiao.user.common.entry.UserDynamicTag;
import com.wepiao.user.common.entry.UserTag;
import com.wepiao.user.common.entry.UserTagValElement;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.redis.RedisKey;
import com.wepiao.user.common.redis.RedisMapper;
import com.wepiao.user.common.redis.RedisUtils;
import com.wepiao.user.common.rest.exception.BaseRestException;

@Component
public class UserTagHandlerImpl implements UserTagHandler {
    private static final Logger  logger = LoggerFactory.getLogger(UserTagHandlerImpl.class);

    @Autowired
    private UserTagMapper        userTagMapper;
    @Autowired
    private UserDynamicTagMapper userDynamicTagMapper;

    @Override
    public boolean addUserTag(String id, OtherID idType, String tag, String tagVal, boolean needPersist, long expire) throws BaseRestException {
        try {
            // 为了存储空间，打标签的时间仅仅存储到秒
            long updateTime = System.currentTimeMillis() / 1000;
            // 计算redis key 的TTL
            int timeToLive = (int) (expire - updateTime);

            // 查询数据库的记录
            UserTag userTag = userTagMapper.queryUserTagById(id, idType);
            UserTagValElement tagValEle = new UserTagValElement(tagVal, updateTime);
            Map<String, UserTagValElement> tagMap = new HashMap<String, UserTagValElement>();
            // Tag表记录为空，插入一条tag记录
            if (null == userTag) {
                tagMap.put(tag, tagValEle);
                userTag = new UserTag(null, id, idType, JSON.toJSONString(tagMap));
                userTagMapper.insertUserTag(userTag);
            } else {
                // 更新tag记录
                String tagJsonStr = userTag.getTag();
                if (null != tagJsonStr) {
                    Map<String, UserTagValElement> oldTagMap = JSON.parseObject(tagJsonStr, new TypeReference<Map<String, UserTagValElement>>() {
                    });
                    if (null != oldTagMap) {
                        oldTagMap.put(tag, tagValEle);
                        userTag.setTag(JSON.toJSONString(oldTagMap));
                        tagMap = oldTagMap;
                    } else {
                        tagMap.put(tag, tagValEle);
                        userTag.setTag(JSON.toJSONString(tagMap));
                    }
                } else {
                    tagMap.put(tag, tagValEle);
                    userTag.setTag(JSON.toJSONString(tagMap));
                }
                userTagMapper.updateUserTag(userTag);
            }

            // Redis操作
            RedisMapper.setUserTagAll(id, idType, tagMap, timeToLive);
        } catch (JedisConnectionException je) {
            logger.error(ResponseInfoEnum.E50002.getMsg(), je);
            throw new BaseRestException(ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(ResponseInfoEnum.E50001.getMsg(), me);
            throw new BaseRestException(ResponseInfoEnum.E50001);
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean removeUserTag(String id, OtherID idType, String tag) throws BaseRestException {
        try {
            // 暂时屏蔽数据库的处理
            //			UserTag uTag = userTagMapper.queryUserTagById(id, idType);
            //			if (null != uTag) {
            //				// Tag表不为空，则检查id下面的tag集合中有无此Tag，有则删掉，没有就nothing to do
            //				String oldTagStringSet = uTag.getTag();
            //				String newTagStringSet = removeTagIfExistedForDB(oldTagStringSet, String.valueOf(tag));
            //				// 如不等于null，则更新tag，若为null则删除tag
            //				if (null != newTagStringSet) {
            //					uTag.setTag(newTagStringSet);
            //					userTagMapper.updateUserTag(uTag);
            //				} else {
            //					userTagMapper.deleteUserTag(id, idType);
            //				}
            //			}
            RedisMapper.removeUserTag(id, idType, tag);
        } catch (JedisConnectionException je) {
            logger.error(ResponseInfoEnum.E50002.getMsg(), je);
            throw new BaseRestException(ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(ResponseInfoEnum.E50001.getMsg(), me);
            throw new BaseRestException(ResponseInfoEnum.E50001);
        }
        return true;
    }

    @Override
    public String queryUserTag(String id, OtherID idType, String tag) throws BaseRestException {
        // 先从redis缓存中取值，如果redis key失效后从db中取值，二方有一个存在记录则认为用户具有该标签,并插入redis
        try {
            String tagVal = RedisMapper.getUserTag(id, idType, tag);
            if (null == tagVal) {
                UserTag userTag = userTagMapper.queryUserTagById(id, idType);
                if (null == userTag) {
                    tagVal = null;
                } else {
                    if (null != userTag.getTag()) {
                        Map<String, UserTagValElement> tagMap = JSON.parseObject(userTag.getTag(),
                                new TypeReference<Map<String, UserTagValElement>>() {
                                });
                        if (null != tagMap && 0 < tagMap.size()) {
                            //写入redis缓存
                            RedisMapper.setUserTagAll(id, idType, tagMap, RedisUtils.DEFAULT_EXPIRE);
                        }
                        UserTagValElement userTagValElement = tagMap.get(tag);
                        if (null != userTagValElement) {
                            tagVal = userTagValElement.getTagVal();
                        }
                    }
                }
            }
            return tagVal;
        } catch (JSONException e) {
            logger.error(LogMsg.BASE_MSG_NO_REQ_ID, LogMsg.JSON_PARSE_FAILED);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.JSON_PARSE_FAILED);
        } catch (JedisConnectionException je) {
            logger.error(ResponseInfoEnum.E50002.getMsg(), je);
            throw new BaseRestException(ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(ResponseInfoEnum.E50001.getMsg(), me);
            throw new BaseRestException(ResponseInfoEnum.E50001);
        }
    }

    @Override
    public Map<String, String> queryAllUserTag(String id, OtherID idType) throws BaseRestException {
        // 先从redis缓存中取值，如果redis key失效后从db中取值，二方有一个存在记录则认为用户具有该标签,并插入redis
        try {
            Map<String, String> tagResultMap = RedisMapper.getAllStaticUserTag(id, idType);
            if (null == tagResultMap || 0 == tagResultMap.size()) {
                UserTag userTag = userTagMapper.queryUserTagById(id, idType);
                if (null == userTag) {
                    tagResultMap = null;
                } else {
                    if (null != userTag.getTag()) {
                        Map<String, UserTagValElement> tagMap = JSON.parseObject(userTag.getTag(),
                                new TypeReference<Map<String, UserTagValElement>>() {
                                });
                        if (null != tagMap && 0 < tagMap.size()) {
                            tagResultMap = new HashMap<String, String>();
                            //设置需写入redis中的属性
                            Set<String> keySet = tagMap.keySet();
                            for (String key : keySet) {
                                UserTagValElement value = tagMap.get(key);
                                String redisValue = String.format(RedisKey.USER_TAG_VALUE, value.getTagVal(), value.getUpdateTime());
                                tagResultMap.put(key, redisValue);
                            }
                            RedisMapper.setUserTagAll(id, idType, tagMap, RedisUtils.DEFAULT_EXPIRE);
                        }
                    }
                }
            }
            return tagResultMap;
        } catch (JSONException e) {
            logger.error(LogMsg.BASE_MSG_NO_REQ_ID, LogMsg.JSON_PARSE_FAILED);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.JSON_PARSE_FAILED);
        } catch (JedisConnectionException je) {
            logger.error(ResponseInfoEnum.E50002.getMsg(), je);
            throw new BaseRestException(ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(ResponseInfoEnum.E50001.getMsg(), me);
            throw new BaseRestException(ResponseInfoEnum.E50001);
        }
    }

    @Override
    public boolean addDynamicUserTag(String id, OtherID idType, String tag, String tagVal, long expire) throws BaseRestException {
        try {
            //动态标签入库
            UserDynamicTag userDynamicTag = addDynamicUserTagToDb(id, idType, tag, tagVal);
            if (null != userDynamicTag) {
                Map<String, String> dynamicUserTagMap = JSON.parseObject(userDynamicTag.getTag(), new TypeReference<Map<String, String>>() {
                });
                if (null != dynamicUserTagMap) {
                    // 为了存储空间，打标签的时间仅仅存储到秒
                    long updateTime = System.currentTimeMillis() / 1000;
                    // 计算redis key 的TTL
                    int timeToLive = (int) (expire - updateTime);
                    // Redis操作
                    RedisMapper.setDynamicUserTagAll(id, idType, dynamicUserTagMap, updateTime, timeToLive);
                }
            }
        } catch (JedisConnectionException je) {
            logger.error(ResponseInfoEnum.E50002.getMsg(), je);
            throw new BaseRestException(ResponseInfoEnum.E50002);
        }

        return true;
    }

    @Override
    public String queryDynamicUserTag(String id, OtherID idType, String tag) throws BaseRestException {
        // 动态标签只从redis缓存中取值
        try {
            String tagVal = RedisMapper.getDynamicUserTag(id, idType, tag);
            //若从redis中取不到,则从db中取
            if (null == tagVal) {
                UserDynamicTag userDynamicTag = userDynamicTagMapper.queryUserDynamicTagById(id, idType);
                if (null == userDynamicTag) {
                    tagVal = null;
                } else {
                    if (null != userDynamicTag.getTag()) {
                        Map<String, String> tagMap = JSON.parseObject(userDynamicTag.getTag(), new TypeReference<Map<String, String>>() {
                        });
                        if (null != tagMap) {
                            tagVal = tagMap.get(tag);
                            RedisMapper.setDynamicUserTagAll(id, idType, tagMap, System.currentTimeMillis() / 1000, 0);
                        }
                    }
                }
            }
            return tagVal;
        } catch (JedisConnectionException je) {
            logger.error(ResponseInfoEnum.E50002.getMsg(), je);
            throw new BaseRestException(ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(ResponseInfoEnum.E50001.getMsg(), me);
            throw new BaseRestException(ResponseInfoEnum.E50001);
        } catch (JSONException e) {
            logger.error(LogMsg.BASE_MSG_NO_REQ_ID, LogMsg.JSON_PARSE_FAILED);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.JSON_PARSE_FAILED);
        }
    }

    @Override
    public Map<String, String> queryAllDynamicUserTag(String id, OtherID idType) throws BaseRestException {
        // 动态标签只从redis缓存中取值
        try {
            Map<String, String> dynamicUserTagMap = RedisMapper.getAllDynamicUserTag(id, idType);
            //若redis中没有则从db中取
            if (null == dynamicUserTagMap || 0 == dynamicUserTagMap.size()) {
                UserDynamicTag userDynamicTag = userDynamicTagMapper.queryUserDynamicTagById(id, idType);
                if (null != userDynamicTag) {
                    if (null != userDynamicTag.getTag()) {
                        dynamicUserTagMap = JSON.parseObject(userDynamicTag.getTag(), new TypeReference<Map<String, String>>() {
                        });
                        if (null != dynamicUserTagMap) {
                            //设置多有动态tag属性到redis
                            RedisMapper.setDynamicUserTagAll(id, idType, dynamicUserTagMap, System.currentTimeMillis() / 1000, 0);
                        }
                    }
                }
            }
            return dynamicUserTagMap;
        } catch (JedisConnectionException je) {
            logger.error(ResponseInfoEnum.E50002.getMsg(), je);
            throw new BaseRestException(ResponseInfoEnum.E50002);
        }
    }

    /**
     * 动态标签入库
     */
    private UserDynamicTag addDynamicUserTagToDb(String id, OtherID idType, String tag, String tagVal) {
        Map<String, String> tagMap = new HashMap<String, String>();
        tagMap.put(tag, tagVal);
        UserDynamicTag userDynamicTagDb = null;
        try {
            // 查询数据库的记录
            userDynamicTagDb = userDynamicTagMapper.queryUserDynamicTagById(id, idType);
            if (null == userDynamicTagDb) {
                userDynamicTagDb = new UserDynamicTag(null, id, idType, JSON.toJSONString(tagMap));
                // Tag表记录为空，插入一条tag记录
                userDynamicTagMapper.insertUserDynamicTag(userDynamicTagDb);
            } else {
                // 更新tag记录
                String dbTagJsonStr = userDynamicTagDb.getTag();
                if (null == dbTagJsonStr) {
                    userDynamicTagDb.setTag(JSON.toJSONString(tagMap));
                } else {
                    Map<String, String> dbTagMap = JSON.parseObject(dbTagJsonStr, new TypeReference<Map<String, String>>() {
                    });
                    if (null != dbTagMap) {
                        dbTagMap.putAll(tagMap);
                        userDynamicTagDb.setTag(JSON.toJSONString(dbTagMap));
                    } else {
                        userDynamicTagDb.setTag(JSON.toJSONString(tagMap));
                    }
                }
                userDynamicTagMapper.updateUserDynamicTag(userDynamicTagDb);
            }
        } catch (DataAccessException me) {
            logger.error(ResponseInfoEnum.E50001.getMsg(), me);
            throw new BaseRestException(ResponseInfoEnum.E50001);
        }
        return userDynamicTagDb;
    }

}
