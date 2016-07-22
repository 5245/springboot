package com.sxk.dao;

import java.util.Map;

import com.sxk.model.Users;

public interface UserDao {

    Users queryOneByUid(Map<String, Object> params);

}