package com.sxk.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam String username, @RequestParam String password) {
        logger.info("request:" + username);
        return username;
    }

    @RequestMapping(value = "/getuserinfo", method = RequestMethod.POST)
    public JSONObject getUserInfo(@RequestBody JSONObject userInfo) {
        return userInfo;
    }
}
