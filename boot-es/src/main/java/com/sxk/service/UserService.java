package com.sxk.service;

import com.sxk.model.UserDO;

public interface UserService {

    UserDO login(String username, String password);
}