package com.pin.flake;

import com.pin.flake.core.ClusterIdMeta;
import com.pin.flake.core.Id;
import com.pin.flake.core.IdMeta;
import com.pin.flake.core.IdResolver;

public class ClusterIdGenerator implements IdGenerator {

    private IdMeta idMeta;

    private IdResolver idResolver;

    private long cluster;

    private long node;

    public ClusterIdGenerator(long cluster, long node) {
        this.idMeta = new ClusterIdMeta();
        this.idResolver = new IdResolver(this.idMeta);
        this.cluster = cluster;
        this.node = node;
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
