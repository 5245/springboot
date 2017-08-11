package com.wepiao.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.wepiao.user.common.redis.RedisUtils;
import com.wepiao.user.common.redis.RedisUtils4StaticTag;

@Slf4j
public class BaseService {
    @Autowired
    protected RedisUtils           redisUtils;
    @Autowired
    protected RedisUtils4StaticTag redisUtils4StaticTag;
}
