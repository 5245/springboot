package com.sxk.service;

import com.sxk.model.ESUser;

public interface ESUserService {

    void save(ESUser esUser);

    ESUser findUser(String id);
}