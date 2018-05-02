package com.pin.service.core;

public interface Extractor<S,T> {

    S extract(T t);
}
