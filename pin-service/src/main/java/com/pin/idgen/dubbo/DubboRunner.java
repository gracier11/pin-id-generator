package com.pin.idgen.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;

public class DubboRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboRunner.class);

    private static volatile boolean running = true;

    private AbstractApplicationContext applicationContext;

    private Class clazzLock;

    public DubboRunner(AbstractApplicationContext applicationContext, Class clazzLock) {
        this.applicationContext = applicationContext;
        this.clazzLock = clazzLock;
    }

    public void start() {
        if(!applicationContext.isRunning()) {
            throw new IllegalStateException("Start spring container before run dubbo！！！");
        }
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        applicationContext.stop();
                        LOGGER.info("Dubbo stopped!");
                    } catch (Throwable t) {
                        LOGGER.error("关闭spring容器报错:{}", t.getMessage());
                    }
                    synchronized (clazzLock) {
                        running = false;
                        clazzLock.notify();
                    }
                }
            });
            /* start spring context before run dubbo！！！
            if(!applicationContext.isRunning()) {
                applicationContext.start();
            }
            */
            LOGGER.info("Dubbo service server started!");
        } catch (RuntimeException e) {
            LOGGER.error("Dubbo service server start failed:{}", e.getMessage());
            System.exit(1);
        }
        synchronized (clazzLock) {
            while (running) {
                try {
                    clazzLock.wait();
                } catch (Throwable e) {}
            }
        }
    }

    public void stop() {
        try {
            if (applicationContext != null) {
                applicationContext.stop();
                applicationContext.close();
                applicationContext = null;
            }
        } catch (Throwable e) {
            LOGGER.error("Dubbo service server stop failed:{}", e.getMessage());
        }
    }
}
