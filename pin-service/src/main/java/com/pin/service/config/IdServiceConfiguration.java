package com.pin.service.config;

import com.pin.service.IdService;
import com.pin.service.core.IdResolver;
import com.pin.service.impl.IdServiceImpl;
import com.pin.service.bean.IdMeta;
import com.pin.service.populater.AtomicIdPopulator;
import com.pin.service.populater.IdPopulator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdServiceConfiguration {

    @Bean
    public IdMeta idMeta() {
        return new IdMeta();
    }

    @Bean
    public IdPopulator idPopulator() {
        return new AtomicIdPopulator();
    }

    @Bean
    public IdResolver longIdResolver() {
        return new IdResolver(idMeta(), idPopulator());
    }

    @Bean
    public IdService idService() {
        return new IdServiceImpl(longIdResolver());
    }
}
