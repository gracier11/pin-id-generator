package com.pin.service.core;

public interface Generator<S,T> {

    T generate(S s);
}
