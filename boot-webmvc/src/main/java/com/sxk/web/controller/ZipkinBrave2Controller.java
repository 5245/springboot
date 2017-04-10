package com.sxk.web.controller;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipkin/brave/service2")
public class ZipkinBrave2Controller {
    @Autowired
    private OkHttpClient okHttpClient;

    @RequestMapping("/test2")
    public String test() throws InterruptedException, IOException {

        Thread.sleep(200);
        Request request3 = new Request.Builder().url("http://localhost:8080/zipkin/brave/service3/test3").build();
        Response response3 = okHttpClient.newCall(request3).execute();
        String response3Str = response3.body().string();

        Request request4 = new Request.Builder().url("http://localhost:8080/zipkin/brave/service4/test4").build();
        Response response4 = okHttpClient.newCall(request4).execute();
        String response4Str = response4.body().string();

        return response3Str + "-" + response4Str;
    }
}
