package com.wepiao.user.common.redis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.alibaba.fastjson.JSONObject;

public class Test {
    @Data
    static class Ca {
        private String name;
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("haha1");
        list.add("haha2");
        list.add("haha3");
        list.forEach(str -> {
            System.out.println(str);
        });

        System.out.println(JSONObject.parseObject("{\"age\":\"dd\"}", Ca.class));
    }

}
