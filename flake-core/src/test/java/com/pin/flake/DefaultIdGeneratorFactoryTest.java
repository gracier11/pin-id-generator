package com.pin.flake;

import org.junit.Assert;
import org.junit.Test;

public class DefaultIdGeneratorFactoryTest {

    private IdGeneratorFactory idGeneratorFactory = new DefaultIdGeneratorFactory();

    @Test
    public void testGenerate() {
        IdGenerator idGenerator = idGeneratorFactory.getIdGenerator();
        Assert.assertTrue(idGenerator instanceof HostIdGenerator);
        Assert.assertFalse(idGenerator instanceof ClusterIdGenerator);
    }

}
