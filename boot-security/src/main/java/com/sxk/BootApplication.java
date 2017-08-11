package com.sxk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

//@SpringBootApplication(scanBasePackages = "com.sxk")
@EnableAutoConfiguration
public class BootApplication {
    public static void main(String[] args) {
        //自动重启
        System.setProperty("spring.devtools.restart.enabled", "false");

        SpringApplication.run(BootApplication.class, args);
    }

}
