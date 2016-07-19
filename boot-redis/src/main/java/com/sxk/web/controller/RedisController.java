package com.sxk.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@RestController
@RequestMapping(value = "/redis")
public class RedisController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired(required = false)
    @Qualifier("shardedJedisPool")
    private ShardedJedisPool    shardedJedisPool;

    @Autowired(required = false)
    @Qualifier("shardedJedisPool4StaticTag")
    private ShardedJedisPool    shardedJedisPool2;

    @RequestMapping(value = "/type", method = RequestMethod.GET)
    public String login(@RequestParam String key) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        String type = jedis.type(key);
        jedis.close();

        ShardedJedis jedis2 = shardedJedisPool2.getResource();
        String type2 = jedis2.type(key);
        jedis2.close();
        return type + ":" + type2;
    }
}
