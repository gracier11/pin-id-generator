package com.pin.service.impl;

public interface Generator<S,T> {

    T generate(S s);
}
