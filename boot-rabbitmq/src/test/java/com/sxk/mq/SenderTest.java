package com.sxk.mq;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxk.BootApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class SenderTest {

    @Autowired
    private Sender sender;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSend() {

        ObjectMapper mapper = new ObjectMapper(); //转换器  
        Map<String, String> msg = new HashMap<>();

        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1000L);

                msg.put("now", new Date().toLocaleString());
                String json = mapper.writeValueAsString(msg); //将对象转换成json  

                sender.send(MQConstants.routingKey4Hello, json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
