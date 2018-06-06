package com.pin.idgen.rpc.api;

import com.pin.idgen.bean.IdMeta;
import com.pin.idgen.rpc.api.bean.Id;
import com.pin.idgen.util.TimeUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IdGenRpcTest {

    @Autowired
    private IdGenRpcService idGenRpc;

    @Autowired
    private IdMeta idMeta;

    @Test
    public void testGenerate() {
        long clusterId = 10, nodeId = 10;
        long timestamp = TimeUtils.currentTimeSeconds();
        long id = idGenRpc.generate(clusterId, nodeId);
        Id ido = idGenRpc.extract(id);
        Assert.assertEquals(clusterId, ido.getCluster());
        Assert.assertEquals(nodeId, ido.getNode());
        Assert.assertTrue(ido.getTimestamp() >= timestamp);
    }

    @Test
    public void testCluster() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("cluster must be positive and can't be greater than " + idMeta.getClusterBitsMask());
        idGenRpc.generate(-1, 0);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("cluster must be positive and can't be greater than " + idMeta.getClusterBitsMask());
        idGenRpc.generate(idMeta.getClusterBitsMask() + 1, 0);

        thrown.expect(Test.None.class);
        idGenRpc.generate(0, 0);

        thrown.expect(Test.None.class);
        idGenRpc.generate(idMeta.getClusterBitsMask(), 0);
    }

    @Test
    public void testNode() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("node must be positive and can't be greater than " + idMeta.getNodeBitsMask());
        idGenRpc.generate(0, -1);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("node must be positive and can't be greater than " + idMeta.getNodeBitsMask());
        idGenRpc.generate(0, idMeta.getNodeBitsMask() + 1);

        thrown.expect(Test.None.class);
        idGenRpc.generate(0, 0);

        thrown.expect(Test.None.class);
        idGenRpc.generate(0, idMeta.getNodeBitsMask());
    }

    @Test
    public void testPerformance() throws InterruptedException {
        final long cluster = 0L;
        final long node = 0L;
        final long[][] times = new long[100][10000];

        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            final int ip = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        long t1 = System.nanoTime();

                        idGenRpc.generate(cluster, node);

                        long t = System.nanoTime() - t1;

                        times[ip][j] = t;
                    }
                }

            };
        }

        long lastMilis = System.currentTimeMillis();

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        System.out
                .println("QPS: "
                        + ((1000000 / (System.currentTimeMillis() - lastMilis)) * 1000));

        long sum = 0;
        long max = 0;
        for (int i = 0; i < times.length; i++) {
            for (int j = 0; j < times[i].length; j++) {
                sum += times[i][j];

                if (times[i][j] > max)
                    max = times[i][j];
            }
        }

        System.out.println("AVG(us): " + sum / 1000 / 1000000);
        System.out.println("MAX(ms): " + max / 1000000);

        // Result is about 400,000 per second
    }

    @Rule
    public ExpectedException thrown= ExpectedException.none();
}
