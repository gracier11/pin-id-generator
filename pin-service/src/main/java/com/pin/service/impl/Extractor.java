package com.pin.service.impl;

public interface Extractor<S,T> {

    S extract(T t);
}
