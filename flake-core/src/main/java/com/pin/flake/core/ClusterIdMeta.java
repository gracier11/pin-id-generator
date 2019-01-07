package com.pin.flake.core;

/**
 * 0 - 00000000 00000000 00000000 00000000 00 - 00000000 00000000 0000 - 00000 - 000000
 * 1位高位符号位
 * 32位时间戳(秒级) 2106-02-07 14:28:15
 * 20位序列，秒内的计数，集群每个节点每秒支持1048575个序号
 * 5位集群，支持31个集群
 * 6位节点，每个集群支持63个节点
 */
public class ClusterIdMeta extends AbstractIdMeta {

    // 高位符号位固定为1位，值0
    private final byte headBits = 1;

    // 时间戳(秒)32位，可用到2106-2-7 14:28:15，endDate = new Date(1000L * ((1L << 32) - 1L))
    private final byte timestampBits = 32;

    // 序号
    private final byte seqBits = 20;

    // 集群
    private final byte clusterBits = 5;

    // 节点
    private final byte nodeBits = 6;

    @Override
    public byte getHeadBits() {
        return headBits;
    }

    @Override
    public byte getTimestampBits() {
        return timestampBits;
    }

    @Override
    public byte getSeqBits() {
        return seqBits;
    }

    @Override
    public byte getClusterBits() {
        return clusterBits;
    }

    @Override
    public byte getNodeBits() {
        return nodeBits;
    }

}
