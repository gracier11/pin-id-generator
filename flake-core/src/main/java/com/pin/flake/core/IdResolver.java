package com.pin.flake.core;

import com.pin.flake.utils.TimeUtils;

import java.util.concurrent.atomic.AtomicReference;

public class IdResolver implements Generator, Extractor<Id, Long> {

    private IdMeta idMeta;

    public IdResolver(IdMeta idMeta) {
        this.idMeta = idMeta;
    }

    @Override
    public Id extract(Long id) {
        if(id == null) throw new IllegalArgumentException("id can't be null");

        long node = id & idMeta.getNodeBitsMask();
        long cluster = (id >>> idMeta.getClusterBitsStartPos()) & idMeta.getClusterBitsMask();
        long seq = (id >>> idMeta.getSeqBitsStartPos()) & idMeta.getSeqBitsMask();
        long timestamp = (id >>> idMeta.getTimestampBitsStartPos()) & idMeta.getTimestampBitsMask();
        return new Id(timestamp, seq, cluster, node);
    }

    @Override
    public long generate(long cluster, long node) {
        if(cluster < 0L || cluster > idMeta.getClusterBitsMask())
            throw new IllegalArgumentException("cluster must be positive and can't be greater than " + idMeta.getClusterBitsMask());
        if(node < 0L || node > idMeta.getNodeBitsMask())
            throw new IllegalArgumentException("node must be positive and can't be greater than " + idMeta.getNodeBitsMask());

        Variant varOld, varNew;
        long timestamp, sequence;

        while (true) {

            // Save the old variant
            varOld = variant.get();

            // populate the current variant
            timestamp = TimeUtils.currentTimeSeconds();
            TimeUtils.validateTimestamp(varOld.lastTimestamp, timestamp);

            sequence = varOld.sequence;

            if (timestamp == varOld.lastTimestamp) {
                sequence = (sequence + 1) & idMeta.getSeqBitsMask();
                if (sequence == 0) {
                    timestamp = TimeUtils.tillNextTimeUnit(varOld.lastTimestamp);
                }
            } else {
                sequence = 0;
            }

            // Assign the current variant by the atomic tools
            varNew = new Variant();
            varNew.sequence = sequence;
            varNew.lastTimestamp = timestamp;

            if (variant.compareAndSet(varOld, varNew)) {
                break;
            }
        }

        Id id = new Id(timestamp, sequence, cluster, node);
        return this._generate(id);
    }

    private long _generate(Id id) {
        long ret = 0;
        ret |= id.getNode();
        ret |= id.getCluster() << idMeta.getClusterBitsStartPos();
        ret |= id.getSeq() << idMeta.getSeqBitsStartPos();
        ret |= id.getTimestamp() << idMeta.getTimestampBitsStartPos();
        return ret;
    }

    class Variant {
        private long sequence = 0;
        private long lastTimestamp = -1;
    }

    private AtomicReference<Variant> variant = new AtomicReference<Variant>(new Variant());
}

