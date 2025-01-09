package com.hydroyura.prodms.warehouse.server.db.entity;

import java.util.Locale;
import java.util.Map;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Material {

    private ObjectId id;

    private String number;
    private String groupNumber;
    private Integer type;
    private Map<Locale, String> name;
    private Map<Locale, String> groupName;
    private String size;
    private Integer profile;
    private String standard;
    private String manufacturer;
    private Integer measureUnit;
    private Double count;

}