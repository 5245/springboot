package com.wepiao.admin.user.test.clarification;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestJedisCluster {

    public static void main(String[] args) throws IOException {
        Set<HostAndPort> clusterNodes = new HashSet<HostAndPort>();
        // 这里只需要列出集群中的一个节点
        // JedisCluster 会自己去 discovery 其他的集群节点
        clusterNodes.add(new HostAndPort("10.104.35.43", 7001));
        JedisCluster cluster = new JedisCluster(clusterNodes);
        //        cluster.set("test1", "test1-val");
        //        cluster.set("test2", "test2-val");
        System.out.println(System.currentTimeMillis());
        cluster.close();

    }

}
