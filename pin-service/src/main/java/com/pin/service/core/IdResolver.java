package com.pin.service.core;

import com.pin.service.bean.Id;
import com.pin.service.bean.IdMeta;
import com.pin.service.populater.IdPopulator;

public class IdResolver implements Generator<Id, Long>, Extractor<Id, Long> {

    private IdMeta idMeta;
    private final long maxTimestamp;
    private final long maxSeq;
    private final long maxCluster;
    private final long maxNode;

    private IdPopulator idPopulator;

    public IdResolver(IdMeta idMeta, IdPopulator idPopulator) {
        this.idMeta = idMeta;
        maxTimestamp = -1L ^ (-1L << idMeta.getTimestampBits());
        maxSeq = -1L ^ (-1L << idMeta.getSeqBits());
        maxCluster = -1L ^ (-1L << idMeta.getClusterBits());
        maxNode = -1L ^ (-1L << idMeta.getNodeBits());
        this.idPopulator = idPopulator;
    }

    @Override
    public Id extract(Long id) {
        long node = id & idMeta.getNodeBitsMask();
        long cluster = (id >>> idMeta.getClusterBitsStartPos()) & idMeta.getClusterBitsMask();
        long seq = (id >>> idMeta.getSeqBitsStartPos()) & idMeta.getSeqBitsMask();
        long timestamp = (id >>> idMeta.getTimestampBitsStartPos()) & idMeta.getTimestampBitsMask();
        return new Id(timestamp, seq, cluster, node);
    }

    @Override
    public Long generate(Id id) {
        this.idPopulator.populateId(id, this.idMeta);

        validate(id);

        return this._generate(id);
    }

    private Long _generate(Id id) {
        long ret = 0;
        ret |= id.getNode();
        ret |= id.getCluster() << idMeta.getClusterBitsStartPos();
        ret |= id.getSeq() << idMeta.getSeqBitsStartPos();
        ret |= id.getTimestamp() << idMeta.getTimestampBitsStartPos();
        return ret;
    }

    private void validate(Id id) {
        if(id == null) throw new IllegalArgumentException("param id can't be null");
        if(id.getTimestamp() <= 0L || id.getTimestamp() > maxTimestamp)
            throw new IllegalArgumentException("timestamp must be a positive long and can't be greater than " + maxTimestamp);
        if(id.getSeq() < 0L || id.getSeq() > maxSeq)
            throw new IllegalArgumentException("sequence must be a positive long and can't be greater than " + maxSeq);
        if(id.getCluster() < 0L || id.getCluster() > maxCluster)
            throw new IllegalArgumentException("cluster must be a positive long and can't be greater than " + maxCluster);
        if(id.getNode() < 0L || id.getNode() > maxNode)
            throw new IllegalArgumentException("node must be a positive long and can't be greater than " + maxNode);
    }

}
