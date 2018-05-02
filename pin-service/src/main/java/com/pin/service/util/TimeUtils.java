package com.pin.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUtils {
    protected static final Logger log = LoggerFactory.getLogger(TimeUtils.class);

    public static void validateTimestamp(long lastTimestamp, long timestamp) {
        if (timestamp < lastTimestamp) {
            if (log.isErrorEnabled())
                log.error(String
                        .format("Clock moved backwards.  Refusing to generate id for %d second/milisecond.",
                                lastTimestamp - timestamp));

            throw new IllegalStateException(
                    String.format(
                            "Clock moved backwards.  Refusing to generate id for %d second/milisecond.",
                            lastTimestamp - timestamp));
        }
    }

    public static long tillNextTimeUnit(final long lastTimestamp) {
        if (log.isInfoEnabled())
            log.info(String
                    .format("Ids are used out during %d. Waiting till next second/milisencond.",
                            lastTimestamp));

        long timestamp = TimeUtils.genTime();
        while (timestamp <= lastTimestamp) {
            timestamp = TimeUtils.genTime();
        }

        if (log.isInfoEnabled())
            log.info(String.format("Next second/milisencond %d is up.",
                    timestamp));

        return timestamp;
    }

    public static long genTime() {
        return System.currentTimeMillis() / 1000;
    }

}
