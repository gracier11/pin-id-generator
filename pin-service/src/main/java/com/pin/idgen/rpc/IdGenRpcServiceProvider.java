package com.pin.idgen.rpc;

import com.pin.idgen.core.IdResolver;
import com.pin.idgen.rpc.api.IdGenRpcService;
import com.pin.idgen.rpc.api.bean.Id;

public class IdGenRpcServiceProvider implements IdGenRpcService {

    private IdResolver idResolver;

    public IdGenRpcServiceProvider(IdResolver idResolver) {
        this.idResolver = idResolver;
    }

    @Override
    public long generate(long cluster, long node) {
        return idResolver.generate(cluster, node);
    }

    @Override
    public Id extract(long id) {
        return idResolver.extract(id);
    }
}
