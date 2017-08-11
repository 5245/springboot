package com.wepiao.config;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wepiao.BootApplication;
import com.wepiao.user.common.dao.UsersMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class UsersTest {

    @Autowired
    private UsersMapper usersMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < 20; i++) {
            int memberId = RandomUtils.nextInt(200000008, 208000008);
            if (0 != memberId) {
                System.out.println(usersMapper.queryOneByUid(memberId));
            }
        }
        System.out.println("耗时s：" + (System.currentTimeMillis() - startTime) / 1000);
    }
}
