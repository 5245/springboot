package com.wepiao.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wepiao.BootApplication;
import com.wepiao.config.prop.RedisProps;
import com.wepiao.user.common.redis.RedisUtils;
import com.wepiao.user.common.redis.RedisUtils4StaticTag;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class RedisPropsTest {

    @Autowired
    private RedisProps           redisProps;
    @Autowired
    private RedisTemplate        redisTemplate;
    @Autowired
    //private RedisOperation redisUtils;
    private RedisUtils           redisUtils;
    @Autowired
    //private RedisOperation redisUtils4StaticTag;
    private RedisUtils4StaticTag redisUtils4StaticTag;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(redisProps.getNumber());
        System.out.println(redisProps.getTimeout());
        System.out.println(redisProps.getMaxActive());
        System.out.println(redisProps.getMaxIdle());
        System.out.println(redisProps.getMinIdle());
        System.out.println(redisProps.isTestOnBorrow());
        System.out.println(redisProps.isPasswordNeed());
        System.out.println(mapper.writeValueAsString(redisProps.getArr()));
        System.out.println(mapper.writeValueAsString(redisProps.getUcHost()));
        System.out.println(mapper.writeValueAsString(redisProps.getTagHost()));
        System.out.println(mapper.writeValueAsString(redisProps.getDtagHost()));

        ValueOperations<String, String> operation = redisTemplate.opsForValue();
        operation.set("key_0801", "value0801");

        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        hash.put("hash_0801", "hash_key_0801_1", "hash0801_1");
        hash.put("hash_0801", "hash_key_0801_2", "hash0801_2");
        hash.put("hash_0801", "hash_key_0801_3", "hash0801_3");

        String value = operation.get("key_0801");
        System.out.println(value);
        String value1 = hash.get("hash_0801", "hash_key_0801_1");
        String value2 = hash.get("hash_0801", "hash_key_0801_2");
        String value3 = hash.get("hash_0801", "hash_key_0801_3");
        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);

        redisUtils.setUsers("ucredis-001", value1, 30);
        redisUtils4StaticTag.set("statictag-001", value1, 30);
        System.out.println(redisUtils.get("ucredis-001"));

        System.out.println(redisUtils4StaticTag.get("statictag-001"));
    }
}
