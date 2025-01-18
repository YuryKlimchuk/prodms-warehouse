package com.hydroyura.prodms.warehouse.server.model.request.material;

import lombok.Data;

@Data
public class GetAllMaterialsReqParams {

    private Integer itemsPerPage;
    private Integer pageNum;
    private String numberLike;
    private Integer sortCode;

}
