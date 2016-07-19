package com.sxk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sxk.service.HelloWorldService;

@Component
public class StartTask implements CommandLineRunner {

    @Autowired
    private HelloWorldService helloWorldService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.helloWorldService.getHelloMessage());
        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

}
