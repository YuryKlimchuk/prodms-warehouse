package com.hydroyura.prodms.warehouse.server.model.event;

import lombok.Data;

@Data
public class MaterialReceipt {

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
