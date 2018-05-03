package com.pin.service.bean;

/**
 * 0 - 00000000 00000000 00000000 00000000 00 - 00000000 00000000 0000 - 00000 - 000000
 * 1位高位符号位
 * 32位时间戳(秒级)
 * 20位序列，秒内的计数，集群每个节点每秒支持1048575个序号
 * 5位集群，支持31个集群
 * 6位节点，每个集群支持63个节点
 */
public class IdMeta {

    public static void main(String[] args) {
        System.out.println(-1L ^ (-1L << 32));
        System.out.println(-1L ^ (-1L << 52));
        System.out.println(-1L ^ (-1L << 63));
        System.out.println(4294967295L/(3600*24*365));
    }

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

    public byte getHeadBits() {
        return headBits;
    }

    public long getHeadBitsStartPos() {
        return timestampBits + seqBits + clusterBits + nodeBits;
    }

    public long getHeadBitsMask() {
        return -1L ^ -1L << headBits;
    }

    public byte getTimestampBits() {
        return timestampBits;
    }

    public long getTimestampBitsStartPos() {
        return seqBits + clusterBits + nodeBits;
    }

    public long getTimestampBitsMask() {
        return -1L ^ -1L << timestampBits;
    }

    public byte getSeqBits() {
        return seqBits;
    }

    public long getSeqBitsStartPos() {
        return clusterBits + nodeBits;
    }

    public long getSeqBitsMask() {
        return -1L ^ -1L << seqBits;
    }

    public byte getClusterBits() {
        return clusterBits;
    }

    public long getClusterBitsStartPos() {
        return nodeBits;
    }

    public long getClusterBitsMask() {
        return -1L ^ -1L << clusterBits;
    }

    public byte getNodeBits() {
        return nodeBits;
    }

    public long getNodeBitsStartPos() {
        return 0L;
    }

    public long getNodeBitsMask() {
        return -1L ^ -1L << nodeBits;
    }
}
