package com.hydroyura.prodms.warehouse.server.controller.swagger;

import com.hydroyura.prodms.warehouse.server.model.api.ApiRes;
import com.hydroyura.prodms.warehouse.server.model.request.material.GetAllMaterialsReqParams;
import com.hydroyura.prodms.warehouse.server.model.response.material.GetAllMaterialsRes;
import com.hydroyura.prodms.warehouse.server.model.response.material.GetMaterialRes;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

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
    ResponseEntity<ApiRes<?>> get(String number, HttpServletRequest request);

    class ApiResGetSuccess extends ApiRes<GetMaterialRes> {
    }
    class ApiResGetBadRequest extends ApiRes<Void> {
    }
    class ApiResGetNotFound extends ApiRes<Void> {
    }


    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = {@Content(schema = @Schema(implementation = ApiResGetAllSuccess.class))},
            description = "Success"
        ),
        @ApiResponse(
            responseCode = "400",
            content = {@Content(schema = @Schema(implementation = ApiResGetAllBadRequest.class))},
            description = "Request params doesn't correspond rules"
        )
    })
    ResponseEntity<ApiRes<?>> getAll(GetAllMaterialsReqParams params, HttpServletRequest request);

    class ApiResGetAllSuccess extends ApiRes<GetAllMaterialsRes> {
    }
    class ApiResGetAllBadRequest extends ApiRes<Void> {
    }



}