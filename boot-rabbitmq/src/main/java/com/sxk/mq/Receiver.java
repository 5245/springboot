package com.sxk.mq;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RabbitListener(queues = MQConstants.routingKey4Hello)
public class Receiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("Receiver: " + msg);
        try {
            ObjectMapper mapper = new ObjectMapper(); //转换器
            Map<String, String> msgObj = mapper.readValue(msg, Map.class);
            System.out.println("date:" + msgObj.get("now"));
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
