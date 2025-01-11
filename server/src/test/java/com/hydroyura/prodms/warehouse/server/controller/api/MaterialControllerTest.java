package com.hydroyura.prodms.warehouse.server.controller.api;

import static com.hydroyura.prodms.warehouse.server.controller.api.ControllerTestUnitils.MATERIAL_NUMBER_1;
import static com.hydroyura.prodms.warehouse.server.controller.api.ControllerTestUnitils.URL_MATERIAL;
import static com.hydroyura.prodms.warehouse.server.controller.api.ControllerTestUnitils.VALIDATION_ERR_MSG;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydroyura.prodms.warehouse.server.model.exception.ValidationException;
import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.request.PatchMaterialCountReq;
import com.hydroyura.prodms.warehouse.server.model.response.GetMaterialRes;
import com.hydroyura.prodms.warehouse.server.service.MaterialService;
import com.hydroyura.prodms.warehouse.server.validation.ValidationManager;
import com.hydroyura.prodms.warehouse.server.validation.enums.NumberKey;
import com.hydroyura.prodms.warehouse.server.validation.model.WrapNumber;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.SimpleErrors;

@WebMvcTest(controllers = MaterialController.class)
class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidationManager validationManager;

    @MockBean
    private MaterialService materialService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void get_OK() throws Exception {
        when(materialService.get(MATERIAL_NUMBER_1))
            .thenReturn(Optional.of(new GetMaterialRes()));

        mockMvc
            .perform(MockMvcRequestBuilders
                .get(URL_MATERIAL + "/" + MATERIAL_NUMBER_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void get_NOT_FOUND() throws Exception {
        when(materialService.get(MATERIAL_NUMBER_1))
            .thenReturn(Optional.empty());

        mockMvc
            .perform(MockMvcRequestBuilders
                .get(URL_MATERIAL + "/" + MATERIAL_NUMBER_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void get_BAD_REQUEST() throws Exception {
        var wrapNumber = new WrapNumber(MATERIAL_NUMBER_1, String.class, NumberKey.MATERIAL);
        var errors = new SimpleErrors(wrapNumber);
        Mockito
            .doThrow(new ValidationException(errors, VALIDATION_ERR_MSG))
            .when(validationManager)
            .validate(wrapNumber, WrapNumber.class);

        mockMvc
            .perform(MockMvcRequestBuilders
                .get(URL_MATERIAL + "/" + MATERIAL_NUMBER_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void create_OK() throws Exception {
        var req = new CreateMaterialReq();
        req.setNumber(MATERIAL_NUMBER_1);

        Mockito
            .doNothing()
            .when(materialService)
            .create(req);

        mockMvc
            .perform(MockMvcRequestBuilders
                .post(URL_MATERIAL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void create_BAD_REQUEST() throws Exception {
        var req = new CreateMaterialReq();
        req.setNumber(MATERIAL_NUMBER_1);

        var errors = new SimpleErrors(req);

        Mockito
            .doThrow(new ValidationException(errors, VALIDATION_ERR_MSG))
            .when(validationManager)
            .validate(req, CreateMaterialReq.class);

        mockMvc
            .perform(MockMvcRequestBuilders
                .post(URL_MATERIAL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void patch_OK() throws Exception {
        var req = new PatchMaterialCountReq();
        req.setDeltaCount(5.5d);
        Mockito
            .when(materialService.patchCount(MATERIAL_NUMBER_1, req))
            .thenReturn(Optional.of(MATERIAL_NUMBER_1));

        mockMvc
            .perform(MockMvcRequestBuilders
                .patch(URL_MATERIAL + "/" + MATERIAL_NUMBER_1  + "/count")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void patch_NOT_FOUND() throws Exception {
        var req = new PatchMaterialCountReq();
        req.setDeltaCount(5.5d);
        when(materialService.patchCount(MATERIAL_NUMBER_1, req))
            .thenReturn(Optional.empty());

        mockMvc
            .perform(MockMvcRequestBuilders
                .patch(URL_MATERIAL + "/" + MATERIAL_NUMBER_1 + "/count")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void patch_BAD_REQUEST() throws Exception {
        var req = new PatchMaterialCountReq();
        req.setDeltaCount(5.5d);
        var errors = new SimpleErrors(req);
        Mockito
            .doThrow(new ValidationException(errors, VALIDATION_ERR_MSG))
            .when(validationManager)
            .validate(req, PatchMaterialCountReq.class);

        mockMvc
            .perform(MockMvcRequestBuilders
                .patch(URL_MATERIAL + "/" + MATERIAL_NUMBER_1 + "/count")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}