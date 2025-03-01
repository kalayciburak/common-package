package com.kalayciburak.commonpackage.core.response.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Response {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    protected LocalDateTime timestamp;
    protected String type;
    protected String code;
    protected Object message;
    protected boolean success;

    public Response(String type, String code, Object message, boolean success) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.code = code;
        this.message = message;
        this.success = success;
    }
}