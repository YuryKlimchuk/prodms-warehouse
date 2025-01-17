package com.hydroyura.prodms.warehouse.server.model.enums;

import lombok.Getter;

@Getter
public enum MaterialType {

    STEEL(1), CAST_IRON(2);

    MaterialType(Integer code) {
        this.code = code;
    }

    private final Integer code;

}
