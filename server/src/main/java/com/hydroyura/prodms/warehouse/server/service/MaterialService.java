package com.hydroyura.prodms.warehouse.server.service;

import com.hydroyura.prodms.warehouse.server.db.repository.MaterialRepository;
import com.hydroyura.prodms.warehouse.server.mapper.CreateMaterialReqToMaterialMapper;
import com.hydroyura.prodms.warehouse.server.mapper.MaterialReceiptToMaterialMapper;
import com.hydroyura.prodms.warehouse.server.mapper.MaterialToGetMaterialResMapper;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialConsumption;
import com.hydroyura.prodms.warehouse.server.model.event.MaterialReceipt;
import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.request.material.GetAllMaterialsReqParams;
import com.hydroyura.prodms.warehouse.server.model.response.material.GetAllMaterialsRes;
import com.hydroyura.prodms.warehouse.server.model.response.material.GetMaterialRes;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialToGetMaterialResMapper materialToGetMaterialResMapper;
    private final CreateMaterialReqToMaterialMapper createMaterialReqToMaterialMapper;
    private final MaterialReceiptToMaterialMapper materialReceiptToMaterialMapper;

    public Optional<GetMaterialRes> get(String number) {
        return materialRepository
            .get(number)
            .map(materialToGetMaterialResMapper::toDestination);
    }

    public GetAllMaterialsRes getAll(GetAllMaterialsReqParams params) {
        return new GetAllMaterialsRes();
    }

    public void createUpdate(MaterialReceipt materialReceipt) {
        var material = materialReceiptToMaterialMapper.toDestination(materialReceipt);
        materialRepository.get(material.getNumber()).ifPresentOrElse(
            m -> materialRepository.patchCount(material.getNumber(), materialReceipt.getCount()),
            () -> materialRepository.create(material)
        );
    }

    public void patchCount(MaterialConsumption materialConsumption) {
        materialRepository.patchCount(materialConsumption.getNumber(), materialConsumption.getCount());
    }

}
