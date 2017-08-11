package com.sxk.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = { "com.sxk" }, exclude = ServletInitializer.class)
@SpringBootApplication(scanBasePackages = { "com.sxk" })
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

}
