package com.pin.idgen.core;

public interface Generator {

    /**
     *
     * @param cluster 集群id
     * @param node 节点id
     * @return
     */
    long generate(long cluster, long node);
}
