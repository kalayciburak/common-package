package com.kalayciburak.commonpackage.core.response.error;

import com.kalayciburak.commonpackage.core.constant.Codes;
import com.kalayciburak.commonpackage.core.constant.Messages;
import com.kalayciburak.commonpackage.core.constant.Types;
import com.kalayciburak.commonpackage.core.response.common.Response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorResponse<T> extends Response {
    private HttpStatus status;
    private ErrorDetail detail;

    /**
     * <b>Var olan bir {@code ErrorResponse} nesnesinden yeni bir hata nesnesi oluşturur.</b>
     *
     * @param response Kopyalanacak hata nesnesi.
     */
    public ErrorResponse(ErrorResponse<?> response) {
        super(response.type, response.code, response.message, false);
        this.status = response.status;
        this.detail = null;
    }

    /**
     * <b>Belirli hata bilgileri ve neden ile yeni bir {@code ErrorResponse} nesnesi oluşturur.</b>
     *
     * @param type    Hata tipi.
     * @param code    Hata kodu.
     * @param message Hata mesajı.
     * @param status  HTTP durum kodu.
     * @param cause   Hata nedeni.
     */
    public ErrorResponse(String type, String code, T message, HttpStatus status, Throwable cause) {
        super(type, code, message, false);
        this.status = status;
        populateErrorDetails(cause);
    }

    /**
     * <b>{@code Throwable} nesnesinden {@code ErrorResponse} nesnesi oluşturur.</b>
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
     * Bu metot, verilen {@code Throwable} nesnesi üzerinden stack trace'i kontrol eder ve mevcutsa hata detaylarını
     * {@code ErrorDetail} nesnesi ile saklar.<br/> Mevcut değilse, hata detayları boş olarak kalır.
     *
     * @param cause Hata nedeni olarak kullanılan {@link Throwable} nesnesi.
     */
    private void populateErrorDetails(Throwable cause) {
        boolean hasStackTrace = cause != null && cause.getStackTrace().length > 0;
        if (hasStackTrace) this.detail = new ErrorDetail(cause);
    }
}