package com.pin.idgen.client;

import com.pin.idgen.rpc.api.IdGenRpcService;
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

        IdGenRpcService idGenRpc = (IdGenRpcService) applicationContext.getBean("idGenerator");
        long id = idGenRpc.generate(0, 0);
        Id _id = idGenRpc.extract(id);
        System.out.println(id);
        System.out.println(_id);
    }
}
