package com.kalayciburak.commonpackage.core.response.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalayciburak.commonpackage.core.constant.Codes;
import com.kalayciburak.commonpackage.core.constant.Messages;
import com.kalayciburak.commonpackage.core.constant.Types;
import com.kalayciburak.commonpackage.core.response.common.Response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * <b>Hata durumlarını temsil eden response nesnesi.</b>
 * <p>
 * ErrorDetail bilgileri frontend'e gönderilmez, sadece backend'de loglanır.
 * Graylog'da traceId ile birlikte aranabilir.
 */
@Setter
@Getter
public class ErrorResponse<T> extends Response {
    private HttpStatus status;

    @JsonIgnore
    private ErrorDetail detail;

    /**
     * <b>Var olan bir {@code ErrorResponse} nesnesinden yeni hata nesnesi oluşturur.</b>
     * <p>
     * TraceId aynı kalır böylece loglarda takip edilebilir.
     *
     * @param response Kopyalanacak hata nesnesi.
     */
    public ErrorResponse(ErrorResponse<?> response) {
        super(response.type, response.code, response.message, false, response.traceId);
        this.status = response.status;
        this.detail = response.detail;
    }

    /**
     * <b>Belirli hata bilgileri ve neden ile yeni bir {@code ErrorResponse} nesnesi oluşturur.</b>
     * <p>
     * Hata detayları internal olarak saklanır ve loglama için kullanılır.
     * Otomatik olarak traceId üretilir ve MDC'ye eklenir.
     *
     * @param type    Hata tipi.
     * @param code    Hata kodu.
     * @param message Kullanıcıya gösterilecek hata mesajı.
     * @param status  HTTP durum kodu.
     * @param cause   Hata nedeni (detaylar için kullanılır).
     */
    public ErrorResponse(String type, String code, T message, HttpStatus status, Throwable cause) {
        super(type, code, message, false);
        this.status = status;
        populateErrorDetails(cause);
    }

    /**
     * <b>{@code Throwable} nesnesinden {@code ErrorResponse} nesnesi oluşturur.</b>
     * <p>
     * Beklenmeyen hatalar için kullanılır. Generic hata mesajı ve HTTP 500 status code ile döner.
     *
     * @param cause Hata nedeni.
     */
    public ErrorResponse(Throwable cause) {
        super(Types.Exception.DEFAULT, Codes.UNEXPECTED, Messages.Error.UNEXPECTED, false);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        populateErrorDetails(cause);
    }

    /**
     * <b>Hata nedenine ait detayları doldurur.</b>
     * <p>
     * Frontend'e gönderilmez, sadece loglama için kullanılır.
     * Graylog'da traceId üzerinden izlenebilir.
     *
     * @param cause Hata nedeni olarak kullanılan {@link Throwable} nesnesi.
     */
    private void populateErrorDetails(Throwable cause) {
        boolean hasStackTrace = cause != null && cause.getStackTrace().length > 0;
        if (hasStackTrace) this.detail = new ErrorDetail(cause);
    }
}