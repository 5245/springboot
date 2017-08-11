package com.wepiao.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDO u = new UserDO();
        u.setUid(1);
        u.setNickName("a");
        u.setMobileNo("123");
        UserDO u2 = new UserDO();
        u2.setUid(2);
        u2.setNickName("b");
        u2.setMobileNo("456");
        byte[] b1 = mapper.writeValueAsBytes(u);
        byte[] b2 = mapper.writeValueAsBytes(u2);

        UserDO u3 = mapper.readValue(b1, 0, b1.length, UserDO.class);
        UserDO u4 = mapper.readValue(b2, 0, b2.length, UserDO.class);
        System.out.println(u.toString());
        System.out.println(u2.toString());
        System.out.println(u3.toString());
        System.out.println(u4.toString());
    }

}
