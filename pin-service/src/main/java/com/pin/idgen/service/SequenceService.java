package com.pin.idgen.service;

public interface SequenceService {

    /**
     *
     * @param cluster 集群id
     * @param node 节点id
     * @return
     */
    long next(long cluster, long node);
}
