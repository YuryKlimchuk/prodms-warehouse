package com.hydroyura.prodms.warehouse.server.model.request;

import lombok.Data;

@Data
public class CreateMaterialReq {

    private String number;
    private String groupNumber;
    private Integer type;
    private String name;
    private String groupName;
    private String size;
    private Integer profile;
    private String standard;
    private Integer measureUnit;
    private Double count;

}
