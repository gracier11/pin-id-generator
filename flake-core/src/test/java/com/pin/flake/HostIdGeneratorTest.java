package com.pin.flake;

import com.pin.flake.core.HostIdMeta;
import com.pin.flake.core.Id;
import com.pin.flake.core.IdMeta;
import com.pin.flake.core.IdResolver;
import com.pin.flake.utils.IpUtils;
import com.pin.flake.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HostIdGeneratorTest {

    private IdMeta idMeta;
    private IdResolver idResolver;
    private IdGenerator idGenerator;

    @Before
    public void setUp() {
        idMeta = new HostIdMeta();
        idResolver = new IdResolver(idMeta);
        idGenerator = new DefaultIdGenerator(idResolver);
    }

    @Test
    public void testGenerate() {
        long[] segments = IpUtils.segments(IpUtils.getLocalHostAddress());
        long clusterId = 0, nodeId = segments[3];
        long timestamp = TimeUtils.currentTimeSeconds();
        long id = idGenerator.generate(clusterId, nodeId);
        Id ido = idGenerator.extract(id);
        Assert.assertEquals(clusterId, ido.getCluster());
        Assert.assertEquals(nodeId, ido.getNode());
        Assert.assertTrue(ido.getTimestamp() >= timestamp);
    }

    @Test
    public void testCluster() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("cluster must be positive and can't be greater than " + idMeta.getClusterBitsMask());
        idGenerator.generate(-1, 0);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("cluster must be positive and can't be greater than " + idMeta.getClusterBitsMask());
        idGenerator.generate(idMeta.getClusterBitsMask() + 1, 0);

        thrown.expect(Test.None.class);
        idGenerator.generate(0, 0);

        thrown.expect(Test.None.class);
        idGenerator.generate(idMeta.getClusterBitsMask(), 0);
    }

    @Test
    public void testNode() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("node must be positive and can't be greater than " + idMeta.getNodeBitsMask());
        idGenerator.generate(0, -1);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("node must be positive and can't be greater than " + idMeta.getNodeBitsMask());
        idGenerator.generate(0, idMeta.getNodeBitsMask() + 1);

        thrown.expect(Test.None.class);
        idGenerator.generate(0, 0);

        thrown.expect(Test.None.class);
        idGenerator.generate(0, idMeta.getNodeBitsMask());
    }

    @Test
    public void testPerformance() throws InterruptedException {
        long[] segments = IpUtils.segments(IpUtils.getLocalHostAddress());
        long clusterId = 0, nodeId = segments[3];
        final long[][] times = new long[100][10000];

        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            final int ip = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        long t1 = System.nanoTime();

                        idGenerator.generate(clusterId, nodeId);

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
