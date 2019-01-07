package com.pin.flake;

import com.pin.flake.core.Id;

public interface IdGenerator {

    long generate();

    long generate(long cluster, long node);

    Id extract(long id);
}
