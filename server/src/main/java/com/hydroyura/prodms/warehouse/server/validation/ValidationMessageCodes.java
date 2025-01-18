package com.hydroyura.prodms.warehouse.server.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessageCodes {

    public static final String DEFAULT_MSG = "INVALID FILED";

    public static final String GET_ALL_MATERIALS_ITEMS_PER_PAGE_MIN = "validation.getAllMaterials.itemsPerPage.min";
    public static final String GET_ALL_MATERIALS_PAGE_NUM_MIN = "validation.getAllMaterials.pageNum.min";
    public static final String GET_ALL_MATERIALS_SORT_CODE_MIN = "validation.getAllMaterials.sortCode.min";
    public static final String GET_ALL_MATERIALS_SORT_CODE_MAX = "validation.getAllMaterials.sortCode.max";

}
