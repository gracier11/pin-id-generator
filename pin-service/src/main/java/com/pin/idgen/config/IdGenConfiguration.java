package com.pin.idgen.config;

import com.pin.idgen.bean.IdMeta;
import com.pin.idgen.core.IdResolver;
import com.pin.idgen.rpc.IdGenRpcServiceProvider;
import com.pin.idgen.rpc.api.IdGenRpcService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGenConfiguration {

    @Bean
    public IdMeta idMeta() {
        return new IdMeta();
    }

    @Bean
    public IdResolver idResolver() {
        return new IdResolver(idMeta());
    }

    @Bean
    public IdGenRpcService idGenRpcService() {
        return new IdGenRpcServiceProvider(idResolver());
    }
}
