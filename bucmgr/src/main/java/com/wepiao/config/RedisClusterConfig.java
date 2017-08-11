package com.wepiao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.wepiao.config.prop.RedisProps;

import redis.clients.jedis.JedisPoolConfig;

//@Configuration
public class RedisClusterConfig {

    public static final String COMMON_SEPARATOR = ":";

    @Autowired
    private RedisProps         redisProps;

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory(redisClusterConfiguration(), jedisPoolConfig());
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxIdle(redisProps.getMaxIdle());
        jedisConfig.setMinIdle(redisProps.getMinIdle());
        jedisConfig.setMaxTotal(redisProps.getMaxActive());
        return jedisConfig;
    }

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        return new RedisClusterConfiguration(redisProps.getClusterHost());
    }

}
