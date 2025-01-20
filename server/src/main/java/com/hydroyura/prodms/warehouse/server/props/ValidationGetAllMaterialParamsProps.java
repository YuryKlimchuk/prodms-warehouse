package com.hydroyura.prodms.warehouse.server.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "validation.targets.get-all-materials")
public class ValidationGetAllMaterialParamsProps {

    private Defaults defaults;
    private Rules rules;

    @Data
    public static class Defaults {
        private Integer itemsPerPage;
        private Integer pageNum;
        private Integer sortCode;
    }

    @Data
    public static class Rules {
        private Integer minItemsPerPage;
        private Integer minPageNum;
        private Integer minSortCode;
        private Integer maxSortCode;
    }

}