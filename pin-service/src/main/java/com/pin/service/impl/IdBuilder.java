package com.pin.service.impl;

import com.pin.service.bean.Id;

public class IdBuilder {

    private long cluster;

    private long node;

    public IdBuilder cluster(long cluster) {
        this.cluster = cluster;
        return this;
    }

    public IdBuilder node(long node) {
        this.node = node;
        return this;
    }

    public Id build() {
        long timestamp = (long) (System.currentTimeMillis() / 1000);
        long seq = 0;
        return new Id(timestamp, seq, cluster, node);
    }
}
