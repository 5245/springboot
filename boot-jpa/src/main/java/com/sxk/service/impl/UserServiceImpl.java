package com.sxk.service.impl;

import org.springframework.stereotype.Service;

import com.sxk.model.UserDO;
import com.sxk.service.UserService;
import com.sxk.web.exception.ServiceException;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDO login(String username, String password) {
        if (null != password && !password.equals("123456")) {
            throw new ServiceException("密码错误");
        }
        return new UserDO(username, "1501234578", "123456@163.com");
    }
}
