package com.hydroyura.prodms.warehouse.server.model.exception;

public class MaterialUpdateCountException extends RuntimeException {

    public MaterialUpdateCountException(String msg) {
        super(msg);
    }

    public MaterialUpdateCountException(String msg, Throwable e) {
        super(msg, e);
    }

}
