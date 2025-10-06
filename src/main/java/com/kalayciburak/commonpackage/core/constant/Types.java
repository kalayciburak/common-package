package com.kalayciburak.commonpackage.core.constant;

public final class Types {
    private Types() {}

    public static final class Response {
        private Response() {}

        public static final String SUCCESS = "SUCCESS";
        public static final String ERROR = "ERROR";
        public static final String WARNING = "WARNING";
        public static final String INFO = "INFO";
    }

    public static final class Error {
        private Error() {}

        public static final String DEFAULT = "ERROR: EXCEPTION";
        public static final String RUNTIME = "ERROR: RUNTIME_EXCEPTION";
        public static final String NOT_FOUND = "ERROR: ENTITY_NOT_FOUND_EXCEPTION";
        public static final String VALIDATION = "ERROR: VALIDATION_EXCEPTION";
        public static final String RESOURCE_NOT_FOUND = "ERROR: RESOURCE_NOT_FOUND_EXCEPTION";
        public static final String REQUEST_PARAMETER = "ERROR: REQUEST_PARAMETER_EXCEPTION";
        public static final String MEDIA_TYPE = "ERROR: MEDIA_TYPE_EXCEPTION";
        public static final String DATA_INTEGRITY = "ERROR: DATA_INTEGRITY_EXCEPTION";
        public static final String WRITING_OUTPUT = "ERROR: WRITING_OUTPUT_EXCEPTION";
        public static final String TYPE_MISMATCH = "ERROR: TYPE_MISMATCH_EXCEPTION";
        public static final String HANDLER = "ERROR: HANDLER_EXCEPTION";
        public static final String FEIGN = "ERROR: FEIGN_EXCEPTION";
        public static final String UNAUTHORIZED = "ERROR: UNAUTHORIZED_EXCEPTION";
        public static final String CONNECTION = "ERROR: CONNECTION_EXCEPTION";
        public static final String JSON_PROCESSING = "ERROR: JSON_PROCESSING_EXCEPTION";
        public static final String KEYCLOAK = "ERROR: KEYCLOAK_EXCEPTION";
        public static final String ILLEGAL_ARGUMENT = "ERROR: ILLEGAL_ARGUMENT_EXCEPTION";
        public static final String ENTITY_NOT_FOUND = "ERROR: ENTITY_NOT_FOUND_EXCEPTION";
        public static final String ENTITY_EXISTS = "ERROR: ENTITY_EXISTS_EXCEPTION";
        public static final String NO_SUCH_ELEMENT = "ERROR: NO_SUCH_ELEMENT_EXCEPTION";
        public static final String TOKEN_TYPE_MISMATCH = "ERROR: TOKEN_TYPE_MISMATCH_EXCEPTION";
        public static final String TOKEN_BLACKLISTED = "ERROR: TOKEN_BLACKLISTED_EXCEPTION";
        public static final String OLD_PASSWORD_NOT_MATCH = "ERROR: OLD_PASSWORD_NOT_MATCH_EXCEPTION";
        public static final String INVALID_JWT = "ERROR: INVALID_JWT_EXCEPTION";
        public static final String BREACHED_PASSWORD = "ERROR: BREACHED_PASSWORD_EXCEPTION";
        public static final String ADMIN_CANNOT_BE_DELETED = "ERROR: ADMIN_CANNOT_BE_DELETED_EXCEPTION";
        public static final String ACCESS_DENIED = "ERROR: ACCESS_DENIED_EXCEPTION";
        public static final String EMAIL_NOT_VERIFIED = "ERROR: EMAIL_NOT_VERIFIED_EXCEPTION";
        public static final String INVALID_VERIFICATION_TOKEN = "ERROR: INVALID_VERIFICATION_TOKEN_EXCEPTION";
    }
}