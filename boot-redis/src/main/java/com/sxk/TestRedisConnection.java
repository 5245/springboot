package com.sxk;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class TestRedisConnection {

    private static final ResourceBundle redisBundle = ResourceBundle.getBundle("redis2");

    public void testMemberRedis() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(redisBundle.getString("redis.pool.maxTotal")));
        String preffix = "redis.server.";
        for (int i = 0; i < 50; i++) {
            if (redisBundle.containsKey(preffix + i + ".ip")) {
                String ip = redisBundle.getString(preffix + i + ".ip");
                int port = Integer.parseInt(redisBundle.getString(preffix + i + ".port"));
                JedisShardInfo shardInfo = new JedisShardInfo(ip, port);
                Jedis jedis = new Jedis(shardInfo);
                Long dbSize = jedis.dbSize();
                System.out.println(i + " : " + ip + " dbsize:" + dbSize);
                jedis.close();
            }
        }
    }

    public void testUCRedis() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(redisBundle.getString("redis.pool.maxTotal")));
        String preffix = "redis.uc.ext.server.";
        for (int i = 0; i < 50; i++) {
            if (redisBundle.containsKey(preffix + i + ".ip")) {
                String ip = redisBundle.getString(preffix + i + ".ip");
                int port = Integer.parseInt(redisBundle.getString(preffix + i + ".port"));
                JedisShardInfo shardInfo = new JedisShardInfo(ip, port);
                Jedis jedis = new Jedis(shardInfo);
                Long dbSize = jedis.dbSize();
                System.out.println(i + " : " + ip + " dbsize:" + dbSize);
                jedis.close();
            }
        }
    }

    public void testStaticTagRedis() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(redisBundle.getString("redis.pool.maxTotal")));
        String preffix = "redis.static.tag.server.";
        for (int i = 0; i < 50; i++) {
            if (redisBundle.containsKey(preffix + i + ".ip")) {
                String ip = redisBundle.getString(preffix + i + ".ip");
                int port = Integer.parseInt(redisBundle.getString(preffix + i + ".port"));
                JedisShardInfo shardInfo = new JedisShardInfo(ip, port);
                Jedis jedis = new Jedis(shardInfo);
                Long dbSize = jedis.dbSize();
                System.out.println(i + " : " + ip + " dbsize:" + dbSize);
                jedis.close();
            }
        }
    }

    public void testDynamicTagRedis() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(redisBundle.getString("redis.pool.maxTotal")));
        String preffix = "redis.dynamic.tag.server.";
        for (int i = 0; i < 50; i++) {
            if (redisBundle.containsKey(preffix + i + ".ip")) {
                String ip = redisBundle.getString(preffix + i + ".ip");
                int port = Integer.parseInt(redisBundle.getString(preffix + i + ".port"));
                JedisShardInfo shardInfo = new JedisShardInfo(ip, port);
                Jedis jedis = new Jedis(shardInfo);
                Long dbSize = jedis.dbSize();
                System.out.println(i + " : " + ip + " dbsize:" + dbSize);
                jedis.close();
            }
        }
    }

    public void testGenUidRedis() {
        String ip = redisBundle.getString("redis.gen.uid.server.ip");
        int port = Integer.parseInt(redisBundle.getString("redis.gen.uid.server.port"));
        JedisShardInfo shardInfo = new JedisShardInfo(ip, port);
        Jedis jedis = new Jedis(shardInfo);
        System.out.println(jedis.get("uid"));
        System.out.println(jedis.ttl("uid"));
        System.out.println("gen.uid: " + ip + " dbsize:" + jedis.dbSize());
        jedis.close();
    }

    public void testMQRedis() {
        String ip = redisBundle.getString("redis.mq.server.ip");
        int port = Integer.parseInt(redisBundle.getString("redis.mq.server.port"));
        JedisShardInfo shardInfo = new JedisShardInfo(ip, port);
        Jedis jedis = new Jedis(shardInfo);
        System.out.println("mq: " + ip + " dbsize:" + jedis.dbSize());
        jedis.close();
    }

    public void testBDPhoneRedis() throws URISyntaxException {
        String uriStr = redisBundle.getString("redis.phone.server.uri");
        String password = redisBundle.getString("redis.phone.server.password");
        URI uri = new URI(uriStr);
        JedisShardInfo shardInfo = new JedisShardInfo(uri);
        shardInfo.setPassword(password);
        Jedis jedis = new Jedis(shardInfo);
        System.out.println("phone: " + uriStr + " dbsize:" + jedis.info());
        jedis.close();
    }

    public void testBDTagRedis() throws URISyntaxException {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(redisBundle.getString("redis.pool.maxTotal")));
        String preffix = "redis.bigdata.tag.write.";
        for (int i = 0; i < 10; i++) {
            if (redisBundle.containsKey(preffix + i + ".uri")) {
                String uriStr = redisBundle.getString(preffix + i + ".uri");
                URI uri = new URI(uriStr);
                JedisShardInfo shardInfo = new JedisShardInfo(uri.getHost(), uri.getPort());
                Jedis jedis = new Jedis(shardInfo);
                jedis.select(2);
                Long dbSize = jedis.dbSize();
                System.out.println(i + " : " + uri + " dbsize:" + dbSize);
                jedis.close();
            }
        }
        System.out.println("========read============");
        String preffix2 = "redis.bigdata.tag.read.";
        for (int i = 0; i < 10; i++) {
            if (redisBundle.containsKey(preffix2 + i + ".uri")) {
                String uriStr = redisBundle.getString(preffix2 + i + ".uri");
                URI uri = new URI(uriStr);
                JedisShardInfo shardInfo = new JedisShardInfo(uri);
                shardInfo.setPassword(null);
                Jedis jedis = new Jedis(shardInfo);
                jedis.select(2);
                Long dbSize = jedis.dbSize();
                System.out.println(i + " : " + uri + " dbsize:" + dbSize);
                jedis.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TestRedisConnection t = new TestRedisConnection();
        System.out.println("===会员中心==============================");
        t.testMemberRedis();
        System.out.println("===用户中心==============================");
        t.testUCRedis();
        System.out.println("===静态标签==============================");
        t.testStaticTagRedis();
        System.out.println("===动态标签==============================");
        t.testDynamicTagRedis();
        System.out.println("===自增主键==============================");
        t.testGenUidRedis();
        System.out.println("===消息队列==============================");
        t.testMQRedis();
        System.out.println("===bdphone==============================");
        t.testBDPhoneRedis();
        System.out.println("===bdtag==============================");
        t.testBDTagRedis();
    }

}
