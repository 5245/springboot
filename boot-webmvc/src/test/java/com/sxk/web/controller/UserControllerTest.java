package com.sxk.web.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUserController() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("test start...");
        // 测试UserController 
        RequestBuilder request = null;

        // 1、get查一下user列表，应该为空 
        request = get("/user/");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));

        request = post("/user/").param("id", "1").param("userName", "测试大师").param("mobileNo", "22222222").param("email", "123@126.com");

        MvcResult result = mvc.perform(request).andExpect(status().isOk()).andReturn();

        System.out.println(JSONObject.toJSONString(result.getResponse().getContentAsString()));

        // 3、get获取user列表，应该有刚才插入的数据 
        request = get("/user/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(
                        content().string(
                                equalTo("[{\"id\":1,\"userName\":\"测试大师\",\"password\":null,\"mobileNo\":\"22222222\",\"email\":\"123@126.com\"}]")));

        // 4、put修改id为1的user 
        request = put("/user/1").param("userName", "测试终极大师").param("mobileNo", "33333333").param("email", "123@126.com");
        mvc.perform(request)
                .andExpect(
                        content().string(
                                equalTo("{\"id\":1,\"userName\":\"测试终极大师\",\"password\":null,\"mobileNo\":\"33333333\",\"email\":\"123@126.com\"}")));

        // 5、get一个id为1的user 
        request = get("/user/1");
        mvc.perform(request)
                .andExpect(
                        content().string(
                                equalTo("{\"id\":1,\"userName\":\"测试终极大师\",\"password\":null,\"mobileNo\":\"33333333\",\"email\":\"123@126.com\"}")));

        // 6、del删除id为1的user 
        request = delete("/user/1");
        mvc.perform(request).andExpect(content().string(equalTo("success")));

        // 7、get查一下user列表，应该为空 
        request = get("/user/");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));

        System.out.println("test end... 用时：" + (System.currentTimeMillis() - startTime));

    }

}
