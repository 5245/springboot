package com.sxk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sxk.bootstrap.BootRedisApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootRedisApplication.class)
@WebAppConfiguration
public class BootRedisApplicationTests {

	@Test
	public void contextLoads() {
	}

}
