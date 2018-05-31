package com.pin.idgen.rpc.api;

import com.pin.idgen.rpc.api.bean.Id;

public interface IdGenRpc {

    long generateId();

    long generateId(long cluster, long node);

    Id extract(long id);
}
