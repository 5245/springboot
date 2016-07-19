package com.sxk.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sxk.service.HelloWorldService;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {
    //从application.properties中取，如果取不到默认值是World
    @Value("${name:World}")
    private String name;

    public String getHelloMessage() {
        return "Hello " + this.name;
    }
}
