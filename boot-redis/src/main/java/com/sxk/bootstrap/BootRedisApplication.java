package com.sxk.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@SpringBootApplication(scanBasePackages = { "com.sxk" }, exclude = ServletInitializer.class)
public class BootRedisApplication {

    private static final ResourceBundle redisBundle = ResourceBundle.getBundle("redis");

    @Bean
    @Qualifier(value = "shardedJedisPool")
    public ShardedJedisPool createJedisPool() {

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(redisBundle.getString("redis.pool.maxTotal")));
        List<JedisShardInfo> shards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (redisBundle.containsKey("redis.server." + i + ".ip")) {
                String ip = redisBundle.getString("redis.server." + i + ".ip");
                int port = Integer.parseInt(redisBundle.getString("redis.server." + i + ".port"));
                JedisShardInfo shard = new JedisShardInfo(ip, port);
                shards.add(shard);
            }
        }
        return new ShardedJedisPool(config, shards);
    }

    @Bean
    @Qualifier(value = "shardedJedisPool4StaticTag")
    public ShardedJedisPool createStaticTagJedisPool() {

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(redisBundle.getString("redis.pool.maxTotal")));
        List<JedisShardInfo> shards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (redisBundle.containsKey("redis.static.tag.server." + i + ".ip")) {
                String ip = redisBundle.getString("redis.static.tag.server." + i + ".ip");
                int port = Integer.parseInt(redisBundle.getString("redis.static.tag.server." + i + ".port"));
                JedisShardInfo shard = new JedisShardInfo(ip, port);
                shards.add(shard);
            }
        }
        return new ShardedJedisPool(config, shards);
    }

    public static void main(String[] args) {
        SpringApplication.run(BootRedisApplication.class, args);
    }

}
