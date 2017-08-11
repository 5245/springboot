package com.wepiao.user.common.redis;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.connection.DataType;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

@Slf4j
public abstract class BaseRedis implements IRedisOperation {

    /**
     * 默认过期时间=30 天,单位秒
     */
    public static final int    DEFAULT_EXPIRE                    = 2592000;

    /**
     * 用户账号被禁用时间 =1天，单位秒
     */
    public static final int    MEMBERSHIP_FROZEN_EXPIRE          = 86400;

    /**
     * 格瓦拉用户信息缓存时间，单位秒
     */
    public static final int    GEWARA_OPENID_EXPIRE              = 604800;

    /**
     * 用户登录失败检查时间 1分钟，单位秒
     */
    public static final int    LOGIN_FAIL_FREQUENCY_CHECK_EXPIRE = 60;

    /**
     * 用户修改密码和重置密码检查时间 1分钟，单位秒
     */
    public static final int    UPDATE_PW_FREQUENCY_CHECK_EXPIRE  = 60;

    /**
     * key的默认延期时间=365天，单位秒
     */
    public static final int    DEFAULT_EXPIRE_DELAY              = 31536000;

    /**
     * 永远不过期时间
     */
    public static final int    NEVER_EXPIRE                      = -1;
    /**
     * 关闭渠道的标示
     */
    public static final String CLOSE_FLAG                        = "quit";
    /**
     * 获取消息时的等待时间,单位秒
     */
    public static final int    BLOCK_TIME                        = 30;

    abstract ShardedJedisPool getJedisPool();

    /*private ShardedJedisPool getJedisPool() {
        return null;
    }*/

    @Override
    public Long remove(String key) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.del(key);
        } catch (JedisException e) {
            errorLog(e, "Redis remove key:%s, err:", key);
            return null;
        }
    }

    /**
     * 设置key的缓存时间
     * @param key
     * @param seconds
     * @return
     */
    @Override
    public Long expire(String key, int seconds) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.expire(key, seconds);
        } catch (JedisException e) {
            errorLog(e, "Redis expire key:%s, err:", key);
            return null;
        }
    }

    /**
     * 持久化，即如果将key设置永不过期
     * @param key
     * @return
     */
    @Override
    public Long persist(String key) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.persist(key);
        } catch (JedisException e) {
            errorLog(e, "Redis persist key:%s, err:", key);
            return null;
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    @Override
    public boolean exists(String key) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.exists(key);
        } catch (JedisException e) {
            errorLog(e, "Redis exists key:%s, err:", key);
            return false;
        }
    }

    /**
     * 判断key的类型
     * @param key
     * @return
     */
    @Override
    public DataType type(String key) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return DataType.fromCode(jedis.type(key));
        } catch (JedisException | IllegalArgumentException e) {
            errorLog(e, "Redis type key:%s, err:", key);
            return null;
        }
    }

    /**
     * 返回key的过期时间,-1:永不过期，-2:key不存在 
     * @param key
     * @return
     */
    @Override
    public Long ttl(String key) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.ttl(key);
        } catch (JedisException e) {
            errorLog(e, "Redis ttl key:%s, err:", key);
            return null;
        }
    }

    /** String start... */
    /**
     * 设置key-value对，值类型为String ,默认过期时间30天
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(String key, String value) {
        return set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 设置key-value对，值类型为String,过期时间单位秒
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(String key, String value, int seconds) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            if (seconds == NEVER_EXPIRE) {
                jedis.set(key, value);
            } else {
                jedis.setex(key, seconds, value);
            }
            return true;
        } catch (JedisException e) {
            errorLog(e, "Redis set key:%s,value:%s, err:", key, value);
            return false;
        }
    }

    /**
     * 获取key的Value值，类型为String 
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.get(key);
        } catch (JedisException e) {
            errorLog(e, "Redis get key:%s, err:", key);
            return null;
        }
    }

    /**
     *设置key-value对，值类型为Object,默认过期时间30天
     * @param key
     * @param obj
     * @return
     */
    @Override
    public boolean setT(String key, Object obj) {
        return setT(key, obj, DEFAULT_EXPIRE);
    }

    /**
     * 设置key-value对，值类型为Object，过期时间单位秒
     * @param key
     * @param obj
     * @param seconds
     * @return
     */
    @Override
    public boolean setT(String key, Object obj, int seconds) {
        String objJson = JSON.toJSONString(obj);
        return set(key, objJson, seconds);
    }

    /**
     * 获取key的json值，反序列化对象返回
     * @param key
     * @param clazz
     * @return
     */
    @Override
    public <T> T getT(String key, Class<T> clazz) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            String value = jedis.get(key);
            return JSONObject.parseObject(value, clazz);
        } catch (JedisException | JSONException e) {
            errorLog(e, "Redis getT key:%s, err:", key);
            return null;
        }
    }

    /**
     * 将key自增，步数为1，并设置key的过期时间
     * @param key
     * @param seconds
     * @return
     */
    @Override
    public Long keyIncr(String key, int seconds) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            Long value = jedis.incr(key);
            if (!jedis.exists(key) && seconds != NEVER_EXPIRE) {
                jedis.expire(key, seconds);
            }
            return value;
        } catch (JedisException e) {
            errorLog(e, "Redis keyIncr key:%s, err:", key);
            return null;
        }
    }

    /**
     * 将key的值自增num
     * @param key
     * @param num
     * @return
     */
    @Override
    public Long incrBy(String key, int num) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.incrBy(key, num);
        } catch (JedisException e) {
            errorLog(e, "Redis incrBy key:%s,num:%d err:", key, num);
            return null;
        }
    }

    /** String start... */
    /** List start... */
    /**
     * 消息发送，将消息放到msgTopic的list中 
     * @param msgTopic
     * @param msg
     * @return
     * @throws JedisException
     */
    @Override
    public boolean sendMsg(String msgTopic, String msg) throws JedisException {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.lpush(msgTopic, msg) > 0L;
        } catch (JedisException e) {
            errorLog(e, "Redis sendMsg msgTopic:%s,msg:%s err:", msgTopic, msg);
            throw e;
        }
    }

    /**
     * 获取消息，使用阻塞pop从mstTopic的list中获取value  
     * @param msgTopic
     * @return
     */
    @Override
    public String rcvMsg(String msgTopic) {
        String msg = null;
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            //当未及时获取到消息时等待30秒后释放连接,防止连接长时间占用
            List<String> msgList = jedis.brpop(BLOCK_TIME, msgTopic);
            if (null != msgList && msgList.size() > 1) {
                //get(0)为msgTopic,get(1)为msg
                msg = msgList.get(1);
            }
        } catch (JedisException e) {
            errorLog(e, "Redis rcvMsg msgTopic:%s, err:", msgTopic);
        }
        return msg;
    }

    /**
     * 消息发布订阅，发布消息，只能是单个redis 
     * @param channel
     * @param msg
     * @return
     */
    @Override
    public boolean pubMsg(String channel, String msg) {
        boolean isSuccess = false;
        Jedis jedis = null;
        try {
            Collection<Jedis> jedisList = getJedisPool().getResource().getAllShards();
            //这里mq的连接池中只有一个jedis连接
            for (Jedis subJedis : jedisList) {
                jedis = subJedis;
            }
            if (null != jedis) {
                jedis.publish(channel, msg);
                isSuccess = true;
            }
        } catch (JedisException e) {
            errorLog(e, "Redis pubMsg channel:%s, err:", channel);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return isSuccess;
    }

    /**
     * 消息发布订阅，订阅消息，只能是单个redis
     * @param listener
     * @param channels
     *
     */
    @Override
    public void subMsg(JedisPubSub listener, String... channels) {
        Jedis jedis = null;
        try {
            Collection<Jedis> jedisList = getJedisPool().getResource().getAllShards();
            //这里mq的连接池中只有一个jedis连接
            for (Jedis subJedis : jedisList) {
                jedis = subJedis;
            }
            if (null != jedis) {
                //此处将会阻塞，在client代码级别为JedisPubSub在处理消息时，将会"独占"链接  
                jedis.subscribe(listener, channels);
            }
        } catch (JedisException e) {
            errorLog(e, "Redis subMsg channels:%s,err:", channels.toString());
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 消息发布订阅，关闭通道
     * @param channel
     */
    @Override
    public void closeChannel(String channel) {
        Jedis jedis = null;
        try {
            Collection<Jedis> jedisList = getJedisPool().getResource().getAllShards();
            //这里mq的连接池中只有一个jedis连接
            for (Jedis subJedis : jedisList) {
                jedis = subJedis;
            }
            if (null != jedis) {
                jedis.publish(channel, CLOSE_FLAG);
                jedis.del(channel);
            }
        } catch (JedisException e) {
            errorLog(e, "Redis closeChannel:%s,err:", channel);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /** List start... */
    /** Hash Set start... */
    /**
     * 设置key-field-value到hash表中，值类型为String，默认过期时间30天
     * @param key
     * @param field
     * @param value
     */
    @Override
    public void hset(String key, String field, String value) {
        hset(key, field, value, DEFAULT_EXPIRE);
    }

    /**
     * 设置key-field-value到hash表中，值类型为String,过期时间单位秒
     * @param key
     * @param field
     * @param value
     */
    @Override
    public void hset(String key, String field, String value, int seconds) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            jedis.hset(key, field, value);
            if (seconds != NEVER_EXPIRE) {
                jedis.expire(key, seconds);
            }
        } catch (JedisException e) {
            errorLog(e, "Redis hset key:%s, err:", key);
        }

    }

    /**
     * 从hash表中获取对应的key-field值，值类型为String
     * @param key
     * @param field
     * @return
     */
    @Override
    public String hget(String key, String field) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.hget(key, field);
        } catch (JedisException | JSONException e) {
            errorLog(e, "Redis hget key:%s, err:", key);
            return null;
        }
    }

    /**
     * 设置key-field-value到hash表中，值类型为String，默认过期时间30天
     * @param key
     * @param field
     * @param value
     */
    @Override
    public void hmset(String key, Map<String, String> fieldMap) {
        hmset(key, fieldMap, DEFAULT_EXPIRE);

    }

    /**
     * 设置key-field-value到hash表中，值类型为String,过期时间单位秒
     * @param key
     * @param field
     * @param value
     */
    @Override
    public void hmset(String key, Map<String, String> fieldMap, int seconds) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            jedis.hmset(key, fieldMap);
            if (seconds != NEVER_EXPIRE) {
                jedis.expire(key, seconds);
            }
        } catch (JedisException e) {
            errorLog(e, "Redis hmset key:%s, err:", key);
        }
    }

    /**
     * 从hash表中获取对应的key-fields值，值类型为List
     * @param key
     * @param field
     * @return
     */
    @Override
    public List<String> hmget(String key, String... fields) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.hmget(key, fields);
        } catch (JedisException e) {
            errorLog(e, "Redis hmget key:%s, err:", key);
            return null;
        }
    }

    /**
     * 设置key-field-value到hash表中，值类型为String，默认过期时间30天
     * @param key
     * @param field
     * @param value
     */
    @Override
    public void hsetAll(String key, Map<String, String> fieldMap) {
        hmset(key, fieldMap, DEFAULT_EXPIRE);

    }

    /**
     * 设置key-field-value到hash表中，值类型为String,过期时间单位秒
     * @param key
     * @param field
     * @param value
     */
    @Override
    public void hsetAll(String key, Map<String, String> fieldMap, int seconds) {
        hmset(key, fieldMap, seconds);
    }

    /**
     * 获取key的hash表
     * @param key
     * @return
     */
    @Override
    public Map<String, String> hgetAll(String key) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.hgetAll(key);
        } catch (JedisException e) {
            errorLog(e, "Redis hgetAll key:%s, err:", key);
            return null;
        }
    }

    /**
     * 判断hash表中key-field是否存在
     * @param key
     * @param field
     * @return
     */
    @Override
    public boolean hexists(String key, String field) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.hexists(key, field);
        } catch (JedisException e) {
            errorLog(e, "Redis hexists key:%s, err:", key);
            return false;
        }
    }

    /**
     * 删除hash表中的fields
     * @param key
     * @param field
     * @return
     */
    @Override
    public boolean hdel(String key, String... fields) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.hdel(key, fields) > 0;
        } catch (JedisException e) {
            errorLog(e, "Redis hdel key:%s, err:", key);
            return false;
        }
    }

    /**
     * 对hash表中的key-field自增num,并设置key的过期时间，默认30天 
     * @param key
     * @param field
     * @param num
     * @return
     */
    @Override
    public Long hincrby(String key, String field, long num) {
        return hincrby(key, field, num, DEFAULT_EXPIRE);
    }

    /**
     * 对hash表中的key-field自增num,并设置key的过期时间，单位秒
     * @param key
     * @param field
     * @param num
     * @param seconds
     * @return
     */
    @Override
    public Long hincrby(String key, String field, long num, int seconds) {
        Long value = null;
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            value = jedis.hincrBy(key, field, num);
            if (seconds != NEVER_EXPIRE) {
                jedis.expire(key, seconds);
            }
        } catch (JedisException e) {
            errorLog(e, "Redis hincrby key:%s, err:", key);
        }
        return value;
    }

    /** Hash Set end... */
    /** Set start... */
    /**
     * 将members加入key的set中 
     * @param key
     * @param members
     */
    @Override
    public void sadd(String key, String... members) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            jedis.sadd(key, members);
        } catch (JedisException e) {
            errorLog(e, "Redis sadd key:%s, err:", key);
        }
    }

    /**
     * 判断set中是否存在元素mmeber 
     * @param key
     * @param member
     * @return
     */
    @Override
    public boolean sismember(String key, String member) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.sismember(key, member);
        } catch (JedisException e) {
            errorLog(e, "Redis sismember key:%s, err:", key);
            return false;
        }
    }

    /**
     * 随机返回set中的某个元素
     * @param key
     * @return
     *
     */
    @Override
    public String srandmember(String key) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.srandmember(key);
        } catch (JedisException e) {
            errorLog(e, "Redis srandmember key:%s, err:", key);
            return null;
        }
    }

    /**
     * 删除set中的members
     * @param key
     * @param members
     *
     */
    @Override
    public void srem(String key, String... members) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            jedis.srem(key, members);
        } catch (JedisException e) {
            errorLog(e, "Redis srem key:%s, err:", key);
        }
    }

    /** Set start... */
    /** Sorted Set start... */
    /**
     * 添加元素到集合，如果存在更新对应的score值,并只是集合的过期时间
     * @param key
     * @param score
     * @param member
     * @param seconds
     */
    @Override
    public void zadd(String key, Integer score, String member, int seconds) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            jedis.zadd(key, score, member);
            if (seconds != NEVER_EXPIRE) {
                jedis.expire(key, seconds);
            }
        } catch (JedisException e) {
            errorLog(e, "Redis zadd key:%s, err:", key);
        }

    }

    /**
     * 删除集合中score在指定区间的元素
     * @param key
     * @param start
     * @param end
     *
     */
    @Override
    public void zremrangeByScore(String key, double start, double end) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            jedis.zremrangeByScore(key, start, end);
        } catch (JedisException e) {
            errorLog(e, "Redis zremrangeByScore key:%s, err:", key);
        }

    }

    /**
     * 返回元素的score
     * @param key
     * @param member
     * @return
     */
    @Override
    public Double zscore(String key, String member) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.zscore(key, member);
        } catch (JedisException e) {
            errorLog(e, "Redis zscore key:%s, err:", key);
            return null;
        }
    }

    /**
     * 统计指定score区间的元素，包含min和max
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public Long zcount(String key, Integer min, Integer max) {
        try (ShardedJedis jedis = getJedisPool().getResource()) {
            return jedis.zcount(key, min, max);
        } catch (JedisException e) {
            errorLog(e, "Redis zscore key:%s, err:", key);
            return null;
        }
    }

    @Override
    public Map<String, Integer> getJedisPoolStatus() {
        ShardedJedisPool pool = getJedisPool();
        Map<String, Integer> statusMap = new HashMap<String, Integer>();
        statusMap.put("numActive", pool.getNumActive());
        statusMap.put("numIdle", pool.getNumIdle());
        statusMap.put("numWaiters", pool.getNumWaiters());
        return statusMap;
    }

    /**
     * @param e
     * @param format
     * @param args
     */
    private void errorLog(Exception e, String format, Object... args) {
        log.error(String.format(format, args), e);
    }

}
