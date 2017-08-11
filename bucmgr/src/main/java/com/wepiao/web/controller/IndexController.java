package com.wepiao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wepiao.config.prop.RedisProps;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private RedisProps redisProps;

    @RequestMapping(value = "/")
    @ResponseBody
    public String index() {
        return redisProps.getNumber() + "";
    }

    @RequestMapping(value = "/t")
    @ResponseBody
    public String index2() {
        return redisProps.getNumber() + "";
    }

}
