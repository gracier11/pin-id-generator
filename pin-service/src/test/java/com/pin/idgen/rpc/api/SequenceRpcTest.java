package com.pin.idgen.rpc.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SequenceRpcTest {

    @Autowired
    private SequenceRpc sequenceRpc;

    @Test
    public void testNext() {
        long clusterId = 10, nodeId = 10;
        long id = 0;
        for(int i = 0; i < 1000000; i++) {
            id = sequenceRpc.next(clusterId, nodeId);
            if(i % 10000 == 0) System.out.println(id);
        }
        System.out.println(id);
    }

}
