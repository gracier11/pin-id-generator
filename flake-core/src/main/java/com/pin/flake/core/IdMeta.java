package com.pin.flake.core;

public interface IdMeta {

    byte getHeadBits();

    long getHeadBitsStartPos();

    long getHeadBitsMask();

    byte getTimestampBits();

    long getTimestampBitsStartPos();

    long getTimestampBitsMask();

    byte getSeqBits();

    long getSeqBitsStartPos();

    long getSeqBitsMask();

    byte getClusterBits();

    long getClusterBitsStartPos();

    long getClusterBitsMask();

    byte getNodeBits();

    long getNodeBitsStartPos();

    long getNodeBitsMask();
}
