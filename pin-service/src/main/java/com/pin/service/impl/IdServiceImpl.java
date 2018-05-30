package com.pin.service.impl;

import com.pin.service.IdService;
import com.pin.service.bean.Id;
import com.pin.service.core.IdResolver;

public class IdServiceImpl implements IdService {

    private IdResolver idResolver;

    public IdServiceImpl(IdResolver idResolver) {
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
