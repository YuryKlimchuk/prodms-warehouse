package com.hydroyura.prodms.warehouse.server.mapper;

public interface OneSideMapper<S,D> {

    D toDestination(S source);

}
