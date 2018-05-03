package com.pin.service.populater;

import com.pin.service.bean.Id;
import com.pin.service.bean.IdMeta;
import com.pin.service.util.TimeUtils;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicIdPopulator implements IdPopulator {

    class Variant {

        private long sequence = 0;
        private long lastTimestamp = -1;

    }

    private AtomicReference<Variant> variant = new AtomicReference<Variant>(new Variant());

    public AtomicIdPopulator() {
        super();
    }

    public void populateId(Id id, IdMeta idMeta) {
        Variant varOld, varNew;
        long timestamp, sequence;

        while (true) {

            // Save the old variant
            varOld = variant.get();

            // populate the current variant
            timestamp = TimeUtils.currentTimeSeconds();
            TimeUtils.validateTimestamp(varOld.lastTimestamp, timestamp);

            sequence = varOld.sequence;

            if (timestamp == varOld.lastTimestamp) {
                sequence = (sequence + 1) & idMeta.getSeqBitsMask();
                if (sequence == 0) {
                    timestamp = TimeUtils.tillNextTimeUnit(varOld.lastTimestamp);
                }
            } else {
                sequence = 0;
            }

            // Assign the current variant by the atomic tools
            varNew = new Variant();
            varNew.sequence = sequence;
            varNew.lastTimestamp = timestamp;

            if (variant.compareAndSet(varOld, varNew)) {
                id.setSeq(sequence);
                id.setTimestamp(timestamp);

                break;
            }

        }
    }

}
