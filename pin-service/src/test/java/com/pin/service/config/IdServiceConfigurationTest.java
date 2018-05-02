package com.pin.service.config;

import com.pin.service.IdService;
import com.pin.service.core.LongIdResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class IdServiceConfigurationTest {

    @Autowired
    private IdService idService;

    @Autowired
    private LongIdResolver longIdResolver;

    @Test
    public void testConfiguration() throws Exception {
        Assert.assertNotNull(idService);
    }

}