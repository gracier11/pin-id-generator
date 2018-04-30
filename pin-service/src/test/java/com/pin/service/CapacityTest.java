package com.pin.service;

import org.junit.Test;

import java.util.Date;

public class CapacityTest {

    @Test
    public void testCapacity() {
        Date to = new Date((-1L ^ (-1L << 32)) * 1000L);
        System.out.println(to);
        System.out.println((new Date((long) (Math.pow(2,31) - 1) * 1000L)));
        System.out.println(-1L ^ (-1L << 20));
    }
}
