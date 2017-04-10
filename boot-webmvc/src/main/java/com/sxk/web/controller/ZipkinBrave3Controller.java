package com.sxk.web.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipkin/brave/service3")
public class ZipkinBrave3Controller {

    @RequestMapping("/test3")
    public String test() throws InterruptedException, IOException {
        Thread.sleep(300);
        return "service3";
    }
}
