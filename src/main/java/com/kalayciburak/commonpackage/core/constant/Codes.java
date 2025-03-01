package com.kalayciburak.commonpackage.core.constant;

public final class Codes {
    public static final String UNEXPECTED = "5000";
    public static final String INVALID_USER = "1000";
    public static final String RESOURCE_NOT_FOUND = "1100";
    public static final String METHOD_ARGUMENT_NOT_VALID = "1200";
    public static final String UNSUPPORTED_ENCODING = "1300";
    public static final String UNSUPPORTED_MEDIA_TYPE = "1400";
    public static final String SERVLET_REQUEST_PARAMETER = "1500";
    public static final String DATA_INTEGRITY_VIOLATION = "1600";
    public static final String MESSAGE_NOT_WRITABLE = "1700";
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH = "1800";
    public static final String NO_HANDLER_FOUND = "1900";
    public static final String FEIGN = "2000";
    public static final String CONNECTION_REFUSED = "2100";
    public static final String PREDICATE_BUILD = "2200";
    public static final String JSON_PROCESSING_EXCEPTION = "2300";
    public static final String KEYCLOAK_EXCEPTION = "2400";
    public static final String UNAUTHORIZED = "2500";
    public static final String USER_NOT_ACTIVE = "2600";
    public static final String USER_NOT_FOUND = "2700";
    public static final String ILLEGAL_ARGUMENT = "2800";
    public static final String ENTITY_NOT_FOUND = "2900";
    public static final String ENTITY_EXISTS = "3000";
    public static final String NO_SUCH_ELEMENT = "3100";

    public static final class Inventory {
        public static final String PRODUCT_NOT_AVAILABLE = "9000";
        public static final String PRODUCT_NOT_FOUND = "9100";
        public static final String CATEGORY_NOT_FOUND = "9200";
    }

    public static final class Auth {
        public static final String TOKEN_TYPE_MISMATCH = "3200";
        public static final String TOKEN_BLACKLISTED = "3300";
        public static final String OLD_PASSWORD_NOT_MATCH = "3400";
        public static final String INVALID_ROLE_IDS = "3500";
        public static final String INVALID_JWT = "3600";
        public static final String BREACHED_PASSWORD = "3700";
        public static final String ADMIN_CANNOT_BE_DELETED = "3800";
        public static final String UNAUTHORIZED = "3900";
        public static final String ACCESS_DENIED = "4000";
    }
}