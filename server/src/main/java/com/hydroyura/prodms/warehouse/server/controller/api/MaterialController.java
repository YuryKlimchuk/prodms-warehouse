package com.hydroyura.prodms.warehouse.server.controller.api;

import static com.hydroyura.prodms.warehouse.server.SharedConstants.REQUEST_ATTR_UUID_KEY;
import static com.hydroyura.prodms.warehouse.server.SharedConstants.REQUEST_TIMESTAMP_KEY;
import static com.hydroyura.prodms.warehouse.server.SharedConstants.RESPONSE_ERROR_MSG_MATERIAL_NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.hydroyura.prodms.warehouse.server.controller.swagger.MaterialDocumentedController;
import com.hydroyura.prodms.warehouse.server.model.api.ApiRes;
import com.hydroyura.prodms.warehouse.server.model.request.material.GetAllMaterialsReqParams;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<ApiRes<?>> get(@PathVariable String number, HttpServletRequest request) {
        validationManager.validate(new WrapNumber<>(number, String.class, NumberKey.MATERIAL), WrapNumber.class);

        var apiRes = buildEmptyApiResponse(request);
        var result = materialService.get(number);

        return result
            .map(apiRes::setData)
            .map(arg -> new ResponseEntity<ApiRes<?>>(arg, HttpStatus.OK))
            .orElseGet(() -> {
                apiRes.getErrors().add(RESPONSE_ERROR_MSG_MATERIAL_NOT_FOUND.formatted(number));
                return new ResponseEntity<>(apiRes, HttpStatus.NOT_FOUND);
            });
    }

    @Override
    @RequestMapping(method = GET, value = "")
    public ResponseEntity<ApiRes<?>> getAll(GetAllMaterialsReqParams params, HttpServletRequest request) {
        validationManager.validate(params, GetAllMaterialsReqParams.class);
        var result = materialService.getAll(params);
        var apiRes = buildEmptyApiResponse(request);
        apiRes.setData(result);
        return ResponseEntity.ok(apiRes);
    }

    private static <T> ApiRes<T> buildEmptyApiResponse(HttpServletRequest req) {
        ApiRes<T> apiResponse = new ApiRes<>();
        apiResponse.setId(extractRequestUUID(req));
        apiResponse.setTimestamp(extractRequestTimestamp(req));
        return apiResponse;
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

}
