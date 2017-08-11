package com.wepiao.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wepiao.config.prop.RedisProps;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisProps redisProps;

    @Bean(name = "jedisPool4Uc")
    public ShardedJedisPool shardedJedisPool4Uc() {
        return new ShardedJedisPool(jedisPoolConfig(), jedisShardInfo4Uc());
    }

    @Bean(name = "jedisPool4StaticTag")
    public ShardedJedisPool shardedJedisPool4staticTag() {
        return new ShardedJedisPool(jedisPoolConfig(), jedisShardInfo4StaticTag());
    }

    @Bean
    public List<JedisShardInfo> jedisShardInfo4Uc() {
        List<JedisShardInfo> shardList = new ArrayList<>();
        List<String> uriList = redisProps.getUcHost();
        uriList.forEach(uri -> {
            shardList.add(new JedisShardInfo(uri));
        });
        return shardList;
    }

    @Bean
    public List<JedisShardInfo> jedisShardInfo4StaticTag() {
        List<JedisShardInfo> shardList = new ArrayList<>();
        List<String> uriList = redisProps.getTagHost();
        uriList.forEach(uri -> {
            shardList.add(new JedisShardInfo(uri));
        });
        return shardList;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxIdle(redisProps.getMaxIdle());
        jedisConfig.setMinIdle(redisProps.getMinIdle());
        jedisConfig.setMaxTotal(redisProps.getMaxActive());
        return jedisConfig;
    }

}
