package com.pin.idgen.rpc.api;

import com.pin.idgen.rpc.api.bean.Id;

public interface IdGenRpcService {

    long generate(long cluster, long node);

    Id extract(long id);
}
