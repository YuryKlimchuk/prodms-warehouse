package com.hydroyura.prodms.warehouse.server.mapper;

public interface TwoSideMapper<S,D> extends OneSideMapper<S,D> {

    S toSource(D destination);

}
