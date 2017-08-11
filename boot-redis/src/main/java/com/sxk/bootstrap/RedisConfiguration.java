package com.sxk.bootstrap;

import java.util.ResourceBundle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisShardInfo;

@Configuration
public class RedisConfiguration {

    private static final ResourceBundle redisBundle = ResourceBundle.getBundle("redis");

    @Bean
    public StringRedisTemplate stringRredisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory());
        // explicitly enable transaction support
        //template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String ip = redisBundle.getString("redis.server.0.ip");
        int port = Integer.parseInt(redisBundle.getString("redis.server.0.port"));
        JedisShardInfo shard = new JedisShardInfo(ip, port);
        shard.setSoTimeout(soTimeout);
        return new JedisConnectionFactory(shard);
    }

}
