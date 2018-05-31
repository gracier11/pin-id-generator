package com.pin.idgen.service;

import com.pin.idgen.util.ByteUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZookeeperSequenceService implements SequenceService {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperSequenceService.class);

    private static final int THRESHOLD = 100000;
    private static final String ROOT = "/_sequence_";

    private CuratorFramework zkClient;

    private final Lock lock = new ReentrantLock();

    private static final Queue<Long> cache = new ConcurrentLinkedQueue<>();

    public ZookeeperSequenceService(CuratorFramework curatorFramework) {
        this.zkClient = curatorFramework;
    }

    @Override
    public long next(long cluster, long node) {
        final String path = ROOT + "/" + cluster + "/" + node;
        long currentSeq = 1L, nextSeq = THRESHOLD + 1;
        lock.lock();
        try {
            if(cache.isEmpty()) {
                zkClient.start();
                try {
                    Stat stat = zkClient.checkExists().forPath(path);
                    if (stat == null) {
                        zkClient.create().creatingParentsIfNeeded().forPath(path, ByteUtils.longToBytes(nextSeq));
                    } else {
                        byte[] data = zkClient.getData().forPath(path);
                        currentSeq = ByteUtils.bytesToLong(data);
                        nextSeq = currentSeq + THRESHOLD;
                        logger.info("currentSeq:{} nextSeq:{}", currentSeq, nextSeq);
                        zkClient.setData().forPath(path, ByteUtils.longToBytes(nextSeq));
                    }
                    for (long i = currentSeq; i < nextSeq; i++) {
                        cache.offer(i);
                    }
                } catch (Exception e) {
                    logger.error("获取序号报错", e);
                } finally {
                    zkClient.close();
                }
            }
        } finally {
            lock.unlock();
        }
        return cache.poll();
    }
}
