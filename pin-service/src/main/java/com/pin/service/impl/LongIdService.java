package com.pin.service.impl;

import com.pin.service.IdService;
import com.pin.service.bean.Id;

public class LongIdService implements IdService {

    private LongIdResolver longIdResolver;

    public LongIdService(LongIdResolver longIdResolver) {
        this.longIdResolver = longIdResolver;
    }

    @Override
    public long generateId() {
        return generateId(0L, 0L);
    }

    @Override
    public long generateId(long cluster, long node) {
        return longIdResolver.generate(new IdBuilder().cluster(cluster).node(node).build());
    }

    @Override
    public Id extract(long id) {
        return longIdResolver.extract(id);
    }
}
