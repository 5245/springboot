package com.sxk.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.sxk.service.SendMessage;

//Dev 测试环境实现类
@Service
@Profile("test")
public class SendMessageImpl implements SendMessage {

    @Override
    public void send() {
        System.out.println("dev send message...");
    }

}
