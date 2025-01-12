package com.hydroyura.prodms.warehouse.server.mapper;

import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import org.mapstruct.Mapper;

@Mapper
public interface CreateMaterialReqToMaterialMapper extends OneSideMapper<CreateMaterialReq, Material> {}
