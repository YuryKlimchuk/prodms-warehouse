package com.hydroyura.prodms.warehouse.server.service;

import com.hydroyura.prodms.warehouse.server.db.repository.MaterialRepository;
import com.hydroyura.prodms.warehouse.server.mapper.MaterialToGetMaterialResMapper;
import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.request.PatchMaterialCountReq;
import com.hydroyura.prodms.warehouse.server.model.response.GetMaterialRes;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialToGetMaterialResMapper materialToGetMaterialResMapper;

    public Optional<GetMaterialRes> get(String number) {
        return materialRepository
            .get(number)
            .map(materialToGetMaterialResMapper::toDestination);
    }

    public Optional<String> patchCount(String number, PatchMaterialCountReq req) {
        materialRepository.patchCount(number, req.getDeltaCount());
        return Optional.empty();
    }

    public void create(CreateMaterialReq req) {



    }

}
