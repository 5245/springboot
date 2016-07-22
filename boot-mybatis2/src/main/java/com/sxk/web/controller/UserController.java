package com.sxk.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sxk.base.dao.RoutingDataSource;
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
        Map<String, Object> params2 = new HashMap<>();
        params2.put("tableIndex", RoutingDataSource.getTableIndex(memberId));
        params2.put("uid", memberId);
        RoutingDataSource.setDataSourceKey(RoutingDataSource.getDbIndex(memberId));
        return userDao.queryOneByUid(params2);
    }
}
