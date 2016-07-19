package com.sxk.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class StartTask implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartTask.class);

    @Value("${app.name:redisApplication}")
    private String              appName;

    @Value("${app.description:application is a Spring Boot Redis application}")
    private String              appDescription;

    @Autowired(required = false)
    @Qualifier("shardedJedisPool")
    private ShardedJedisPool    shardedJedisPool;

    @Override
    public void run(String... args) throws Exception {
        logger.info(appName + "  start.....");
        System.out.println(appDescription);

        ShardedJedis jedis = shardedJedisPool.getResource();
        System.out.println(jedis.type("utag:389B23D516DE0032EE52F5FE67C82B7F:0"));
        jedis.close();
    }

}
