package com.pin.idgen.rpc;

import com.pin.idgen.core.IdResolver;
import com.pin.idgen.rpc.api.IdGenRpc;
import com.pin.idgen.rpc.api.bean.Id;

public class IdGenRpcProvider implements IdGenRpc {

    private IdResolver idResolver;

    public IdGenRpcProvider(IdResolver idResolver) {
        this.idResolver = idResolver;
    }

    @Override
    public long generateId() {
        return generateId(0L, 0L);
    }

    @Override
    public long generateId(long cluster, long node) {
        return idResolver.generate(cluster, node);
    }

    @Override
    public Id extract(long id) {
        return idResolver.extract(id);
    }
}
