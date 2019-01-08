package com.pin.flake;

import com.pin.flake.core.Id;

public interface IdGenerator {

    long generate();

    Id extract(long id);
}
