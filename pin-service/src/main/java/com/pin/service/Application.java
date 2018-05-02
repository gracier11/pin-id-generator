package com.pin.service;

import com.pin.dubbo.DubboRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.AbstractApplicationContext;

@SpringBootApplication
@ComponentScan({"com.pin"})
@ImportResource({"classpath:spring-context.xml"})
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AbstractApplicationContext applicationContext = (AbstractApplicationContext) SpringApplication.run(Application.class, args);
        // start dubbo
        DubboRunner dubboRunner = new DubboRunner(applicationContext, Application.class);
        dubboRunner.start();
    }

}
