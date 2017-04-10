package com.sxk.web.controller;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipkin/brave/service1")
public class ZipkinBraveController {
    @Autowired
    private OkHttpClient okHttpClient;

    @RequestMapping("/test1")
    public String test() throws InterruptedException, IOException {
        Thread.sleep(100);
        Request request = new Request.Builder().url("http://localhost:8080/zipkin/brave/service2/test2").build();
        /*
         * 1、执行execute()的前后，会执行相应的拦截器（cs,cr）
         * 2、请求在被调用方执行的前后，也会执行相应的拦截器（sr,ss）
        */
        //利用OkHttpClient调用发起http请求。在每次发起请求时则需要通过brave记录Span信息，并异步传递给zipkin
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
