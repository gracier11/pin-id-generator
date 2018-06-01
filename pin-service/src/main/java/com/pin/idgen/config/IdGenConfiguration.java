package com.pin.idgen.config;

import com.pin.idgen.bean.IdMeta;
import com.pin.idgen.core.IdResolver;
import com.pin.idgen.properties.ZookeeperProperties;
import com.pin.idgen.rpc.IdGenRpcProvider;
import com.pin.idgen.rpc.SequenceRpcProvider;
import com.pin.idgen.rpc.api.IdGenRpc;
import com.pin.idgen.rpc.api.SequenceRpc;
import com.pin.idgen.service.ZookeeperSequenceService;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGenConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "zookeeper")
    public ZookeeperProperties zookeeperProperties() {
        return new ZookeeperProperties();
    }

    @Bean
    public CuratorFramework curatorZkClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zookeeperProperties().getUrl(), retryPolicy);
        curatorFramework.start();
        return curatorFramework;
    }

    @Bean
    public ZookeeperSequenceService zookeeperSequenceService() {
        return new ZookeeperSequenceService(curatorZkClient());
    }

    @Bean
    public SequenceRpc sequenceRpc() {
        return new SequenceRpcProvider(zookeeperSequenceService());
    }

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
