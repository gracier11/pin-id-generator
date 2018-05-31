package com.pin.idgen.config;

import com.pin.idgen.rpc.api.IdGenRpc;
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
    private IdGenRpc idService;

    @Test
    public void testConfiguration() throws Exception {
        Assert.assertNotNull(idService);
    }

}