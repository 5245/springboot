package com.sxk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxk.dao.ESUserRepository;
import com.sxk.model.ESUser;
import com.sxk.service.ESUserService;

@Service
public class ESUserServiceImpl implements ESUserService {

    @Autowired
    private ESUserRepository esUserRepository;

    @Override
    public void save(ESUser esUser) {
        esUserRepository.save(esUser);

    }

    @Override
    public ESUser findUser(String id) {
        ESUser esUser = esUserRepository.findOne(id);
        return esUser;
    }

}
