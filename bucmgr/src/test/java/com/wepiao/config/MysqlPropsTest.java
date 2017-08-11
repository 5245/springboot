package com.wepiao.config;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wepiao.BootApplication;
import com.wepiao.config.prop.MysqlProps;
import com.wepiao.config.prop.RedisProps;
import com.wepiao.config.prop.MysqlProps.JdbcProp;
import com.wepiao.user.common.redis.RedisUtils;
import com.wepiao.user.common.redis.RedisUtils4StaticTag;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class MysqlPropsTest {

    @Autowired
    private RedisProps           redisProps;
    @Autowired
    private MysqlProps           mysqlProps;
    @Autowired
    private RedisTemplate        redisTemplate;
    @Autowired
    private RedisUtils           redisUtils;
    @Autowired
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
        List<JdbcProp> list = mysqlProps.getUc();
        System.out.println(mapper.writeValueAsString(mysqlProps.getIdBackup()));
        System.out.println(mapper.writeValueAsString(list));
    }
}
