package com.pin.flake;

import com.pin.flake.core.Id;
import com.pin.flake.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HostIdGeneratorTest {

    private HostIdGenerator idGenerator;

    @Before
    public void setUp() {
        idGenerator = new HostIdGenerator();
    }

    @Test
    public void testGenerate() {
        long timestamp = TimeUtils.currentTimeSeconds();
        long id = idGenerator.generate();
        Id ido = idGenerator.extract(id);
        Assert.assertEquals(this.idGenerator.getCluster(), ido.getCluster());
        Assert.assertEquals(this.idGenerator.getNode(), ido.getNode());
        Assert.assertTrue(ido.getTimestamp() >= timestamp);
    }

    @Test
    public void testPerformance() throws InterruptedException {
        final long[][] times = new long[100][10000];

        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            final int ip = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        long t1 = System.nanoTime();

                        idGenerator.generate();

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
