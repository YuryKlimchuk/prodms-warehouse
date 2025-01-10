package com.hydroyura.prodms.warehouse.server.service;

import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.request.PatchMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.response.GetMaterialRes;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {


    public Optional<GetMaterialRes> get(String number) {
        return Optional.empty();
    }

    public void patch(String number, PatchMaterialReq req) {
    }

    public void create(CreateMaterialReq req) {

    }

}
