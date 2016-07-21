package com.sxk.dao;

import com.sxk.model.Users;

public interface UserDao {

    Users queryOneByUid();
    Users queryOneByUid(int uid);

}