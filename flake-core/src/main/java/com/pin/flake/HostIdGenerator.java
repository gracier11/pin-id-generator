package com.pin.flake;

import com.pin.flake.core.HostIdMeta;
import com.pin.flake.core.Id;
import com.pin.flake.core.IdMeta;
import com.pin.flake.core.IdResolver;
import com.pin.flake.utils.IpUtils;

public class HostIdGenerator implements IdGenerator {

    private IdMeta idMeta;

    private IdResolver idResolver;

    private long cluster = 0;

    private long node;

    public HostIdGenerator() {
        this.idMeta = new HostIdMeta();
        this.idResolver = new IdResolver(this.idMeta);
        long[] segments = IpUtils.segments(IpUtils.getLocalHostAddress());
        this.node = segments[3];
    }

    @Override
    public long generate() {
        return idResolver.generate(cluster, node);
    }

    @Override
    public Id extract(long id) {
        return idResolver.extract(id);
    }

    public IdMeta getIdMeta() {
        return idMeta;
    }

    public IdResolver getIdResolver() {
        return idResolver;
    }

    public long getCluster() {
        return cluster;
    }

    public long getNode() {
        return node;
    }
}
