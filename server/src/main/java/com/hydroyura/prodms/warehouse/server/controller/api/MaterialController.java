package com.hydroyura.prodms.warehouse.server.controller.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.hydroyura.prodms.warehouse.server.controller.swagger.MaterialDocumentedController;
import com.hydroyura.prodms.warehouse.server.model.api.ApiRes;
import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.request.PatchMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.response.GetMaterialRes;
import com.hydroyura.prodms.warehouse.server.service.MaterialService;
import com.hydroyura.prodms.warehouse.server.validation.ValidationManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(
    value = "/api/v1/materials",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialController implements MaterialDocumentedController {

    private final ValidationManager validationManager;
    private final MaterialService materialService;

    @Override
    @RequestMapping(method = GET, value = "/{number}")
    public ResponseEntity<ApiRes<GetMaterialRes>> get(String number, HttpServletRequest request) {
        return null;
    }

    @Override
    @RequestMapping(method = POST, value = "")
    public ResponseEntity<ApiRes<Void>> create(CreateMaterialReq req, HttpServletRequest request) {
        return null;
    }

    @Override
    @RequestMapping(method = PATCH, value = "")
    public ResponseEntity<ApiRes<Void>> patch(PatchMaterialReq req, HttpServletRequest request) {
        return null;
    }
}
