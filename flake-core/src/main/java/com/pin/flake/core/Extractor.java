package com.pin.flake.core;

public interface Extractor<S,T> {

    S extract(T t);
}
