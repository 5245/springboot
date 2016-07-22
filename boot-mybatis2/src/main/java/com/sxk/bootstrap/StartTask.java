package com.sxk.bootstrap;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sxk.base.dao.RoutingDataSource;
import com.sxk.dao.UserDao;

/**
 * @description 实现这个commandLineRunner接口后，spring启动容器就去执行，多个的话，可以用@Order定义执行顺序
 * @author sxk
 * @date 2016年7月21日
 */

@Component
public class StartTask implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartTask.class);

    @Value("${app.name:mybatisApplication}")
    private String              appName;

    @Value("${app.description:application is a Spring Boot Mybatis application}")
    private String              appDescription;

    @Autowired
    private UserDao             userDao;

    @Override
    public void run(String... args) throws Exception {
        logger.info(appName + "  start.....");
        System.out.println(appDescription);

        /*System.out.println(userDao.queryOneByUid());

        System.out.println(userDao.queryOneByUid(859)); //tableIndex=100
        System.out.println(userDao.queryOneByUid(1033)); //tableIndex=200
        System.out.println(userDao.queryOneByUid(187)); //tableIndex=300
        System.out.println(userDao.queryOneByUid(604)); //tableIndex=400
        System.out.println(userDao.queryOneByUid(633)); //tableIndex=512
        System.out.println(userDao.queryOneByUid(354)); //tableIndex=640
        System.out.println(userDao.queryOneByUid(840)); //tableIndex=768
        System.out.println(userDao.queryOneByUid(1371)); //tableIndex=1000
        */

        long startTime = System.currentTimeMillis();
        for (int i = 1; i < 100; i++) {
            int memberId = RandomUtils.nextInt(100);
            if (0 != memberId) {
                Map<String, Object> params = new HashMap<>();
                params.put("tableIndex", RoutingDataSource.getTableIndex(memberId));
                params.put("uid", memberId);
                RoutingDataSource.setDataSourceKey(RoutingDataSource.getDbIndex(memberId));
                System.out.println(userDao.queryOneByUid(params));
            }
        }
        System.out.println("耗时s：" + (System.currentTimeMillis() - startTime) / 1000);
    }

}
