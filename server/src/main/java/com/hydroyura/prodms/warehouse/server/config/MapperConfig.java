package com.hydroyura.prodms.warehouse.server.config;

import com.hydroyura.prodms.warehouse.server.mapper.CreateMaterialReqToMaterialMapper;
import com.hydroyura.prodms.warehouse.server.mapper.CreateMaterialReqToMaterialMapperImpl;
import com.hydroyura.prodms.warehouse.server.mapper.MaterialReceiptToMaterialMapper;
import com.hydroyura.prodms.warehouse.server.mapper.MaterialReceiptToMaterialMapperImpl;
import com.hydroyura.prodms.warehouse.server.mapper.MaterialToGetMaterialResMapper;
import com.hydroyura.prodms.warehouse.server.mapper.MaterialToGetMaterialResMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    MaterialToGetMaterialResMapper materialToGetMaterialResMapper() {
        return new MaterialToGetMaterialResMapperImpl();
    }

    @Bean
    CreateMaterialReqToMaterialMapper createMaterialReqToMaterialMapper() {
        return new CreateMaterialReqToMaterialMapperImpl();
    }

    @Bean
    MaterialReceiptToMaterialMapper materialReceiptToMaterialMapper() {
        return new MaterialReceiptToMaterialMapperImpl();
    }

}
