package com.pin.service.bean;

import java.io.Serializable;

public class Id implements Serializable {
    private static final long serialVersionUID = -8378918575577205355L;

    /** 秒级 */
    private long timestamp;

    /** 序号 **/
    private long seq;

    /** 集群 */
    private long cluster;

    /** 节点 */
    private long node;

    public Id(long timestamp, long seq, long cluster, long node) {
        this.timestamp = timestamp;
        this.seq = seq;
        this.cluster = cluster;
        this.node = node;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getCluster() {
        return cluster;
    }

    public void setCluster(long cluster) {
        this.cluster = cluster;
    }

    public long getNode() {
        return node;
    }

    public void setNode(long node) {
        this.node = node;
    }

}
