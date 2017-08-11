package com.wepiao.user.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedisPool;

@Component
public class RedisUtils extends BaseRedis {

    @Autowired(required = false)
    @Qualifier("jedisPool4Uc")
    private ShardedJedisPool shardedJedisPool;

    @Override
    ShardedJedisPool getJedisPool() {
        return shardedJedisPool;
    }

    public void setUsers(String key, String value, int second) {
        set(key, value, second);
    }

}
