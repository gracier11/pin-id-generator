package com.pin.service.impl;

import com.pin.service.bean.IdBuilder;
import com.pin.service.IdService;
import com.pin.service.core.IdResolver;
import com.pin.service.bean.Id;

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
        return idResolver.generate(new IdBuilder().cluster(cluster).node(node).build());
    }

    @Override
    public Id extract(long id) {
        return idResolver.extract(id);
    }
}
