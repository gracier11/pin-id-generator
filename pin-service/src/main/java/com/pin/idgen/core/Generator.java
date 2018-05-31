package com.pin.idgen.core;

public interface Generator {

    Long generate(long cluster, long node);
}
