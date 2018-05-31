package com.pin.idgen.rpc;

import com.pin.idgen.rpc.api.SequenceRpc;
import com.pin.idgen.service.SequenceService;

public class SequenceRpcProvider implements SequenceRpc {

    private SequenceService sequenceService;

    public SequenceRpcProvider(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    @Override
    public long next() {
        return this.next(0, 0);
    }

    @Override
    public long next(long cluster, long node) {
        return sequenceService.next(cluster, node);
    }
}
