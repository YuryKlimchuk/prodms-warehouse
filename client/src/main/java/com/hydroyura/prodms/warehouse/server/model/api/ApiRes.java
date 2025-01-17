package com.hydroyura.prodms.warehouse.server.model.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import lombok.Data;

@Data
public class ApiRes<T> {

    private UUID id;
    private Timestamp timestamp;
    private T data;
    private Collection<String> warnings = new ArrayList<>();
    private Collection<String> errors = new ArrayList<>();

}
