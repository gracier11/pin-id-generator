package com.pin.service.impl.populater;

import com.pin.service.bean.Id;
import com.pin.service.impl.bean.IdMeta;

public class SyncIdPopulator extends BasePopulator {

    public SyncIdPopulator() {
        super();
    }

    public synchronized void populateId(Id id, IdMeta idMeta) {
        super.populateId(id, idMeta);
    }

}
