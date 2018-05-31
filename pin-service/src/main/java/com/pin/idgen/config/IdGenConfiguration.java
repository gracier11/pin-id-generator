package com.pin.idgen.config;

import com.pin.idgen.bean.IdMeta;
import com.pin.idgen.core.IdResolver;
import com.pin.idgen.rpc.IdGenRpcProvider;
import com.pin.idgen.rpc.api.IdGenRpc;
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
    public IdGenRpc idGenRpc() {
        return new IdGenRpcProvider(idResolver());
    }
}
