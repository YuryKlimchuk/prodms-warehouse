package com.hydroyura.prodms.warehouse.server.model.enums;

import lombok.Getter;

@Getter
public enum MaterialProfile {

    HEX(1), CIRCLE(2), RECTANGLE(3), PIPE(4);

    MaterialProfile(Integer code) {
        this.code = code;
    }

    private final Integer code;

}
