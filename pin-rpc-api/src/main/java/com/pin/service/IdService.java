package com.pin.service;

import com.pin.service.bean.Id;

public interface IdService {

    long generateId();

    long generateId(long cluster, long node);

    Id extract(long id);
}
