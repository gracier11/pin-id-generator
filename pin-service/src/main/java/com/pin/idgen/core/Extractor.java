package com.pin.idgen.core;

public interface Extractor<S,T> {

    S extract(T t);
}
