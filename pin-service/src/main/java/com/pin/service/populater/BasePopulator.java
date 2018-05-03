package com.pin.service.populater;

import com.pin.service.bean.Id;
import com.pin.service.bean.IdMeta;
import com.pin.service.util.TimeUtils;

public abstract class BasePopulator implements IdPopulator {
    protected long sequence = 0;
    protected long lastTimestamp = -1;

    public BasePopulator() {
        super();
    }

    public void populateId(Id id, IdMeta idMeta) {
        long timestamp = TimeUtils.currentTimeSeconds();
        TimeUtils.validateTimestamp(lastTimestamp, timestamp);

        if (timestamp == lastTimestamp) {
            sequence++;
            sequence &= idMeta.getSeqBitsMask();
            if (sequence == 0) {
                timestamp = TimeUtils.tillNextTimeUnit(lastTimestamp);
            }
        } else {
            lastTimestamp = timestamp;
            sequence = 0;
        }

        id.setSeq(sequence);
        id.setTimestamp(timestamp);
    }

}
