package com.sxk.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sxk.model.ESUser;
import com.sxk.model.UserDO;
import com.sxk.service.ESUserService;
import com.sxk.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService         userService;

    @Autowired
    private ESUserService       esUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public UserDO login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @RequestMapping(value = "/getuserinfo", method = RequestMethod.POST)
    public JSONObject getUserInfo(@RequestBody JSONObject userInfo) {
        return userInfo;
    }

    @RequestMapping(value = "/login2es", method = RequestMethod.GET)
    public String login2es(@RequestParam String username, @RequestParam String password) {
        String id = System.currentTimeMillis() + "";
        ESUser esUser = new ESUser(id, username, password, "1593", 18, "123@126.com");
        esUserService.save(esUser);
        return "success";
    }

}
