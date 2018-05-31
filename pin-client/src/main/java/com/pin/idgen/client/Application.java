package com.pin.idgen.client;

import com.pin.idgen.rpc.api.IdGenRpc;
import com.pin.idgen.rpc.api.bean.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.AbstractApplicationContext;

@SpringBootApplication
@ComponentScan({"com.pin.idgen"})
@ImportResource({"classpath:spring-context.xml"})
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AbstractApplicationContext applicationContext = (AbstractApplicationContext) SpringApplication.run(Application.class, args);

        IdGenRpc idGenRpc = (IdGenRpc) applicationContext.getBean("idGenRpc");
        long id = idGenRpc.generateId();
        Id _id = idGenRpc.extract(id);
        System.out.println(id);
        System.out.println(_id);
    }
}
