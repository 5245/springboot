package com.sxk.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @description 实现这个commandLineRunner接口后，spring启动容器就去执行，多个的话，可以用@Order定义执行顺序
 * @author sxk
 * @date 2016年7月21日
 */

@Component
public class StartTask implements CommandLineRunner {

    @Value("${app.name}")
    private String appName;

    @Override
    public void run(String... args) {
        System.out.println(appName + " is running...");
    }
}
