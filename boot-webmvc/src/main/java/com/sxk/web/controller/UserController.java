package com.sxk.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sxk.model.UserDO;
import com.sxk.service.UserService;

@Api(value = "user-CRUD-api")
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService         userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public UserDO login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @RequestMapping(value = "/getuserinfo", method = RequestMethod.POST)
    public JSONObject getUserInfo(@RequestBody JSONObject userInfo) {
        return userInfo;
    }

    // 创建线程安全的Map 
    private static Map<Integer, UserDO> users = Collections.synchronizedMap(new HashMap<Integer, UserDO>());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserDO> getUserList() {
        List<UserDO> r = new ArrayList<UserDO>(users.values());
        return r;
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDO getUser(@ApiParam(value = "用户ID") @PathVariable Integer id) {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        return users.get(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public UserDO addUser(@RequestBody @Validated UserDO user, BindingResult bindingResult) {
        this.valid(bindingResult);
        users.put(user.getId(), user);
        return user;
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "UserDO") })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserDO updateUser(@PathVariable Integer id, @RequestBody UserDO user) throws InterruptedException {
        UserDO u = users.get(id);
        u.setUserName(user.getUserName());
        u.setMobileNo(user.getMobileNo());
        users.put(id, u);
        Thread.sleep(100);
        return u;
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Integer id) {
        users.remove(id);
        return "success";
    }

}
