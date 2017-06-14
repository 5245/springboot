package com.sxk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sxk.bootstrap.BootApplication;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = BootApplication.class)
@SpringBootTest(classes = BootApplication.class)
@WebAppConfiguration
public class BootRedisApplicationTest {

    @Test
    public void contextLoads() {
    }

}
