package com.wepiao.user.common.redis;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.DataType;

import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisException;

public interface IRedisOperation {

    Long remove(String key);

    Long expire(String key, int seconds);

    Long persist(String key);

    boolean exists(String key);

    DataType type(String key);

    Long ttl(String key);

    /** String start... */
    boolean set(String key, String value);

    boolean set(String key, String value, int seconds);

    String get(String key);

    boolean setT(String key, Object obj);

    boolean setT(String key, Object obj, int seconds);

    <T> T getT(String key, Class<T> clazz);

    Long keyIncr(String key, int seconds);

    Long incrBy(String key, int num);

    /** String end... */
    /** List start... */

    boolean sendMsg(String msgTopic, String msg) throws JedisException;

    String rcvMsg(String msgTopic);

    boolean pubMsg(String channel, String msg);

    void subMsg(JedisPubSub listener, String... channels);

    void closeChannel(String channel);

    /** List end... */
    /** Hash Set start... */

    void hset(String key, String field, String value);

    void hset(String key, String field, String value, int seconds);

    String hget(String key, String field);

    void hmset(String key, Map<String, String> fieldMap);

    void hmset(String key, Map<String, String> fieldMap, int seconds);

    List<String> hmget(String key, String... fields);

    void hsetAll(String key, Map<String, String> fieldMap);

    void hsetAll(String key, Map<String, String> fieldMap, int seconds);

    Map<String, String> hgetAll(String key);

    boolean hexists(String key, String field);

    boolean hdel(String key, String... fields);

    Long hincrby(String key, String field, long num);

    Long hincrby(String key, String field, long num, int seconds);

    /** Hash Set end... */
    /** Set start... */
    void sadd(String key, String... members);

    boolean sismember(String key, String member);

    String srandmember(String key);

    void srem(String key, String... members);

    /** Set end... */
    /** Sorted Set start... */
    void zadd(String key, Integer score, String member, int seconds);

    void zremrangeByScore(String key, double start, double end);

    Double zscore(String key, String member);

    Long zcount(String key, Integer min, Integer max);

    /** Sorted Set start... */

    Map<String, Integer> getJedisPoolStatus();

}
