package com.hydroyura.prodms.warehouse.server.db.entity;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Material {

    private ObjectId id;

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