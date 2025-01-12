package com.hydroyura.prodms.warehouse.server.controller.api;

import static com.hydroyura.prodms.warehouse.server.SharedConstants.REQUEST_ATTR_UUID_KEY;
import static com.hydroyura.prodms.warehouse.server.SharedConstants.REQUEST_TIMESTAMP_KEY;
import static com.hydroyura.prodms.warehouse.server.SharedConstants.RESPONSE_ERROR_MSG_ENTITY_NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.hydroyura.prodms.warehouse.server.controller.swagger.MaterialDocumentedController;
import com.hydroyura.prodms.warehouse.server.model.api.ApiRes;
import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.request.PatchMaterialCountReq;
import com.hydroyura.prodms.warehouse.server.model.response.GetMaterialRes;
import com.hydroyura.prodms.warehouse.server.service.MaterialService;
import com.hydroyura.prodms.warehouse.server.validation.ValidationManager;
import com.hydroyura.prodms.warehouse.server.validation.enums.NumberKey;
import com.hydroyura.prodms.warehouse.server.validation.model.WrapNumber;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        validationManager.validate(new WrapNumber(number, String.class, NumberKey.MATERIAL), WrapNumber.class);
        var result = materialService.get(number);
        return buildApiResponseOkOrNotFound(result, request, number);
    }

    @Override
    @RequestMapping(method = POST, value = "")
    public ResponseEntity<ApiRes<Void>> create(CreateMaterialReq req, HttpServletRequest request) {
        validationManager.validate(req, CreateMaterialReq.class);
        ApiRes<Void> emptyApiResponse = buildEmptyApiResponse(request);
        return materialService.create(req)
            .map(arg -> new ResponseEntity<>(emptyApiResponse, HttpStatus.NO_CONTENT))
            .orElseGet(() -> {
               emptyApiResponse.getErrors().add("Number = [%s] already exists".formatted(req.getNumber()));
               return new ResponseEntity<>(emptyApiResponse, HttpStatus.BAD_REQUEST);
            });
    }

    @Override
    @RequestMapping(method = PATCH, value = "/{number}/count")
    public ResponseEntity<ApiRes<Void>> patchCount(String number, PatchMaterialCountReq req, HttpServletRequest request) {
        validationManager.validate(req, PatchMaterialCountReq.class);
        var result = materialService.patchCount(number, req);
        return buildApiResponseNotContentOrNotFound(result.isPresent(), request, number);
    }

    private static <T> ApiRes<T> buildEmptyApiResponse(HttpServletRequest req) {
        ApiRes<T> apiResponse = new ApiRes<>();
        apiResponse.setId(extractRequestUUID(req));
        apiResponse.setTimestamp(extractRequestTimestamp(req));

        return apiResponse;
    }

    private static <T> ResponseEntity<ApiRes<T>> buildApiResponseOkOrNotFound(Optional<T> data, HttpServletRequest req, Object number) {
        ApiRes<T> apiResponse = new ApiRes<>();
        apiResponse.setId(extractRequestUUID(req));
        apiResponse.setTimestamp(extractRequestTimestamp(req));
        data.ifPresentOrElse(
            apiResponse::setData,
            () -> apiResponse.getErrors().add(RESPONSE_ERROR_MSG_ENTITY_NOT_FOUND.formatted(number))
        );

        ResponseEntity<ApiRes<T>> responseEntity;
        if (data.isPresent()) {
            responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    private static UUID extractRequestUUID(HttpServletRequest request) {
        return Optional
            .ofNullable(request.getAttribute(REQUEST_ATTR_UUID_KEY))
            .map(UUID.class::cast)
            .orElseThrow(RuntimeException::new);
    }

    private static Timestamp extractRequestTimestamp(HttpServletRequest request) {
        return Optional
            .ofNullable(request.getAttribute(REQUEST_TIMESTAMP_KEY))
            .map(Timestamp.class::cast)
            .orElseThrow(RuntimeException::new);
    }

    private static <T> ResponseEntity<ApiRes<T>> buildApiResponseNotContent(HttpServletRequest req) {
        ApiRes<T> apiResponse = new ApiRes<>();
        apiResponse.setId(extractRequestUUID(req));
        apiResponse.setTimestamp(extractRequestTimestamp(req));
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }

    private static ResponseEntity<ApiRes<Void>> buildApiResponseNotContentOrNotFound(Boolean flag, HttpServletRequest req, Object number) {
        ApiRes<Void> apiResponse = new ApiRes<>();
        apiResponse.setId(extractRequestUUID(req));
        apiResponse.setTimestamp(extractRequestTimestamp(req));

        ResponseEntity<ApiRes<Void>> responseEntity;
        if (flag) {
            responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }
}
