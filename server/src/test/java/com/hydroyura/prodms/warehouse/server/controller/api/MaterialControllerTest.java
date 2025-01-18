package com.hydroyura.prodms.warehouse.server.controller.api;

import static com.hydroyura.prodms.warehouse.server.controller.api.ControllerTestUnitils.MATERIAL_NUMBER_1;
import static com.hydroyura.prodms.warehouse.server.controller.api.ControllerTestUnitils.URL_MATERIAL;
import static com.hydroyura.prodms.warehouse.server.controller.api.ControllerTestUnitils.VALIDATION_ERR_MSG;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydroyura.prodms.warehouse.server.model.exception.ValidationException;
import com.hydroyura.prodms.warehouse.server.model.request.CreateMaterialReq;
import com.hydroyura.prodms.warehouse.server.model.response.material.GetMaterialRes;
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
        var wrapNumber = new WrapNumber<>(MATERIAL_NUMBER_1, String.class, NumberKey.MATERIAL);
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
    void getAll_OK() throws Exception {
        throw new RuntimeException();
    }

    @Test
    void getAll_BAD_REQUEST() throws Exception {
        throw new RuntimeException();
    }

}