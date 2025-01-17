package com.hydroyura.prodms.warehouse.server.mapper;

import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.hydroyura.prodms.warehouse.server.model.response.GetMaterialRes;
import org.mapstruct.Mapper;

@Mapper
public interface MaterialToGetMaterialResMapper extends OneSideMapper<Material, GetMaterialRes> {
}
