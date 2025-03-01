package com.kalayciburak.commonpackage.core.response.error;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorDetail {
    private String className;
    private String methodName;
    private Integer lineNumber;
    private String debugMessage;

    /**
     * <b>Varsayılan hata detaylarını doldurur.</b>
     *
     * @param cause Hata nedeni.
     */
    public ErrorDetail(Throwable cause) {
        this.className = cause.getStackTrace()[0].getClassName();
        this.methodName = cause.getStackTrace()[0].getMethodName();
        this.lineNumber = cause.getStackTrace()[0].getLineNumber();
        this.debugMessage = cause.getMessage();
    }
}