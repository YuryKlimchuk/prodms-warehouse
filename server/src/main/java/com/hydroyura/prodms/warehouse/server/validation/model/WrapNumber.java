package com.hydroyura.prodms.warehouse.server.validation.model;


import com.hydroyura.prodms.warehouse.server.validation.enums.NumberKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrapNumber<T> {

    private T number;
    private Class<T> type;
    private NumberKey key;

}
