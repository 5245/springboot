package com.sxk.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxk.model.UserDO;

@Controller
public class IndexController {

    @RequestMapping("/")
    public void index() {
        Map<Integer, Object> map = new HashMap<>();
        Random r = new Random();
        Integer id = r.nextInt(10000);
        while (true) {
            UserDO u = new UserDO(
                    id,
                    "aaaaaaaaaaaaaaaaassssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssaaasssssssssssssssssssssssssssssssssssssaaaaaaaa",
                    "bbbbbbbbbbbbbbbssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssbbbbbbbbbbbbbbbb");
            map.put(id, u);
            System.out.println(u.toString());
        }
    }
}
