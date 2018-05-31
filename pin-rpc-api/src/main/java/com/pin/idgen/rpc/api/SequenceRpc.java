package com.pin.idgen.rpc.api;

public interface SequenceRpc {

    long next();

    long next(long cluster, long node);
}
