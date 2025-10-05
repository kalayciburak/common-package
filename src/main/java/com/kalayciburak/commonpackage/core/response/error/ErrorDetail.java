package com.kalayciburak.commonpackage.core.response.error;

import lombok.Getter;
import lombok.Setter;

/**
 * <b>Hata detaylarını loglama amacıyla saklayan DTO.</b>
 * <p>
 * Bu bilgiler frontend'e gönderilmez ({@code @JsonIgnore} ile işaretlenmiştir).
 * Sadece Graylog'da traceId ile birlikte kaydedilir ve hata takibi için kullanılır.
 */
@Setter
@Getter
public class ErrorDetail {
    private String className;
    private String methodName;
    private Integer lineNumber;
    private String debugMessage;
    private String exceptionType;

    /**
     * <b>Throwable nesnesinden hata detaylarını çıkarır.</b>
     * <p>
     * Stack trace'in ilk elemanından sınıf adı, metod adı ve satır numarası alınır.
     * Exception mesajı ve tipi de saklanır.
     *
     * @param cause Hata nedeni.
     */
    public ErrorDetail(Throwable cause) {
        if (cause != null && cause.getStackTrace().length > 0) {
            this.className = cause.getStackTrace()[0].getClassName();
            this.methodName = cause.getStackTrace()[0].getMethodName();
            this.lineNumber = cause.getStackTrace()[0].getLineNumber();
            this.debugMessage = cause.getMessage();
            this.exceptionType = cause.getClass().getSimpleName();
        }
    }

    /**
     * <b>Hata detaylarını okunabilir string formatında döner.</b>
     * <p>
     * Loglama için kullanılır.
     *
     * @return Formatlanmış hata detayı string'i.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s.%s:%d - %s",
                exceptionType != null ? exceptionType : "Unknown",
                className != null ? className : "Unknown",
                methodName != null ? methodName : "unknown",
                lineNumber != null ? lineNumber : 0,
                debugMessage != null ? debugMessage : "No message");
    }
}