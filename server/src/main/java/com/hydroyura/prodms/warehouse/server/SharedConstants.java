package com.hydroyura.prodms.warehouse.server;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SharedConstants {

    public static final String EX_MSG_MATERIAL_PATCH_COUNT = "Material's count was not updated";

    public static final String LOG_MSG_GOT_REQUEST = "Got request to uri = [{}], uuid = [{}], ts = [{}]";

    public static final String REQUEST_LOG_ID_HEADER_NAME = "Request-log-id";

    public static final String REQUEST_ATTR_UUID_KEY = "REQUEST_UUID";
    public static final String REQUEST_TIMESTAMP_KEY = "REQUEST_TIMESTAMP";
    public static final String REQUEST_URI_KEY = "REQUEST_URI";


    public static final String RESPONSE_ERROR_MSG_MATERIAL_NOT_FOUND = "Material with number = [%s] not found";

}
