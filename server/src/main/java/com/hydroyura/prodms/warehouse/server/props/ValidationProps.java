package com.hydroyura.prodms.warehouse.server.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "validation")
public class ValidationProps {

    private Boolean enabled;

}
