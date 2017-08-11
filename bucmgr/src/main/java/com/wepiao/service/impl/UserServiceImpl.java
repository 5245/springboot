package com.wepiao.service.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.wepiao.service.BaseService;
import com.wepiao.service.UserService;

@Slf4j
@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Override
    public void getUserByMemberId(Integer memberId) {
        redisUtils.get(memberId + "");
    }

}
