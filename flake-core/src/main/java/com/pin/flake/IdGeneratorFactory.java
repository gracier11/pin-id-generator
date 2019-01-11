package com.pin.flake;

public interface IdGeneratorFactory {

    default IdGenerator getIdGenerator() {
        return new HostIdGenerator();
    }
}
