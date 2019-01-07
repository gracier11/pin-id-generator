package com.pin.flake.core;

public abstract class AbstractIdMeta implements IdMeta {

    @Override
    public long getHeadBitsStartPos() {
        return getTimestampBits() + getSeqBits() + getClusterBits() + getNodeBits();
    }

    @Override
    public long getHeadBitsMask() {
        return -1L ^ -1L << getHeadBits();
    }

    @Override
    public long getTimestampBitsStartPos() {
        return getSeqBits() + getClusterBits() + getNodeBits();
    }

    @Override
    public long getTimestampBitsMask() {
        return -1L ^ -1L << getTimestampBits();
    }

    @Override
    public long getSeqBitsStartPos() {
        return getClusterBits() + getNodeBits();
    }

    @Override
    public long getSeqBitsMask() {
        return -1L ^ -1L << getSeqBits();
    }

    @Override
    public long getClusterBitsStartPos() {
        return getNodeBits();
    }

    @Override
    public long getClusterBitsMask() {
        return -1L ^ -1L << getClusterBits();
    }

    @Override
    public long getNodeBitsStartPos() {
        return 0L;
    }

    @Override
    public long getNodeBitsMask() {
        return -1L ^ -1L << getNodeBits();
    }
}
