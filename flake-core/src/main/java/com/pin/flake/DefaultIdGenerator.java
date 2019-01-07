package com.pin.flake;

import com.pin.flake.core.Id;
import com.pin.flake.core.IdResolver;

public class DefaultIdGenerator implements IdGenerator {

    private IdResolver idResolver;

    private long cluster;

    private long node;

    public DefaultIdGenerator(IdResolver idResolver) {
        this.idResolver = idResolver;
    }

    public DefaultIdGenerator(IdResolver idResolver, long cluster, long node) {
        this.idResolver = idResolver;
        this.cluster = cluster;
        this.node = node;
    }

    @Override
    public long generate() {
        return generate(this.cluster, this.node);
    }

    @Override
    public long generate(long cluster, long node) {
        return idResolver.generate(cluster, node);
    }

    @Override
    public Id extract(long id) {
        return idResolver.extract(id);
    }
}
