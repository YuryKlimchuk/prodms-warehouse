package com.hydroyura.prodms.warehouse.server.mapper;

import com.hydroyura.prodms.warehouse.server.db.entity.Material;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import org.mapstruct.Mapper;

@Mapper
public interface MaterialReceiptToMaterialMapper extends OneSideMapper<MaterialReceipt, Material> {}
