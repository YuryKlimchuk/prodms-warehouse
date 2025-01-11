package com.hydroyura.prodms.warehouse.server.controller.swagger;

import com.hydroyura.prodms.warehouse.server.model.api.ApiRes;
import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.request.PatchMaterialCountReq;
import com.hydroyura.prodms.warehouse.server.model.response.GetMaterialRes;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface MaterialDocumentedController {


    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ApiResGetSuccess.class))},
            description = "Success"
        ),
        @ApiResponse(
            responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ApiResGetBadRequest.class))},
            description = "Material number doesn't correspond required rules"
        ),
        @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema(implementation = ApiResGetNotFound.class))},
            description = "Material with getting number doesn't exist"
        )
    })
    ResponseEntity<ApiRes<GetMaterialRes>> get(@PathVariable String number, HttpServletRequest request);

    class ApiResGetSuccess extends ApiRes<GetMaterialRes> {
    }
    class ApiResGetBadRequest extends ApiRes<Void> {
    }
    class ApiResGetNotFound extends ApiRes<Void> {
    }


    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            content = {@Content(schema = @Schema(implementation = ApiResCreateSuccess.class))},
            description = "Material was created successful"
        ),
        @ApiResponse(
            responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ApiResCreateBadRequest.class))},
            description = "Can't create material with given data"
        )
    })
    ResponseEntity<ApiRes<Void>> create(@RequestBody CreateMaterialReq req , HttpServletRequest request);

    class ApiResCreateSuccess extends ApiRes<Void> {
    }
    class ApiResCreateBadRequest extends ApiRes<Void> {
    }


    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            content = {@Content(schema = @Schema(implementation = ApiResPatchSuccess.class))},
            description = "Material count was patched successful"
        ),
        @ApiResponse(
            responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ApiResPatchBadRequest.class))},
            description = "Material number doesn't correspond required rules"
        ),
        @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema(implementation = ApiResPatchNotFound.class))},
            description = "Material with getting number doesn't exist"
        )
    })
    ResponseEntity<ApiRes<Void>> patchCount(@PathVariable String number,
                                       @RequestBody PatchMaterialCountReq req ,
                                       HttpServletRequest request);

    class ApiResPatchSuccess extends ApiRes<Void> {
    }

    class ApiResPatchBadRequest extends ApiRes<Void> {
    }

    class ApiResPatchNotFound extends ApiRes<Void> {
    }


}