package com.sxk.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sxk.dao.UserDao;
import com.sxk.model.Users;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDao             userDao;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam String username, @RequestParam String password) {
        logger.info("request:" + username);
        return username;
    }

    @RequestMapping(value = "/getUserByMemberId", method = RequestMethod.GET)
    public Users getUserByMemberId(@RequestParam Integer memberId) {
        return userDao.queryOneByUid(memberId);
    }
}
