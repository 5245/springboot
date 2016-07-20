package com.sxk.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@RestController
@RequestMapping(value = "/redis")
public class RedisController extends BaseController {

    private static final Logger      logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired(required = false)
    @Qualifier("shardedJedisPool")
    private ShardedJedisPool         shardedJedisPool;

    @Autowired(required = false)
    @Qualifier("shardedJedisPool4StaticTag")
    private ShardedJedisPool         shardedJedisPool2;

    @Autowired
    private StringRedisTemplate      stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    @RequestMapping(value = "/type", method = RequestMethod.GET)
    public String login(@RequestParam String key) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        String type = jedis.type(key);
        jedis.close();

        ShardedJedis jedis2 = shardedJedisPool2.getResource();
        String type2 = jedis2.type(key);
        jedis2.close();

        DataType dataType = stringRedisTemplate.type(key);

        if (dataType == DataType.HASH) {
            BoundHashOperations<String, String, String> hashValue = stringRedisTemplate.boundHashOps(key);
            System.out.println(hashValue.getKey());
        } else if (dataType == DataType.STRING) {
            BoundValueOperations<String, String> value = stringRedisTemplate.boundValueOps(key);
            System.out.println(value.getKey());
            System.out.println(value.get());
        }

        DataType dataType1 = redisTemplate.type(key);

        return type + ":" + type2 + ":" + dataType.name() + ":" + dataType1.name();
    }
}
