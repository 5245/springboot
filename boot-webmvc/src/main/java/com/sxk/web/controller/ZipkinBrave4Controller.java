package com.sxk.web.controller;

import java.io.IOException;

import okhttp3.OkHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipkin/brave/service4")
public class ZipkinBrave4Controller {
    @Autowired
    private OkHttpClient okHttpClient;

    @RequestMapping("/test4")
    public String test() throws InterruptedException, IOException {
        Thread.sleep(300);
        return "service3";
    }
}
